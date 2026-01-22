package org.thingsboard.server.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.common.data.goview.*;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.goview.GoViewFileService;
import org.thingsboard.server.dao.goview.GoViewProjectDataService;
import org.thingsboard.server.dao.goview.GoViewProjectService;
import org.thingsboard.server.queue.util.TbCoreComponent;


import java.io.IOException;

import java.util.List;
import java.util.UUID;

@RestController
@TbCoreComponent
@RequiredArgsConstructor
@RequestMapping("/api/goview/project")
public class GoViewProjectController extends BaseController {
    @Resource
    private GoViewProjectService goViewProjectService;

    @Resource
    private GoViewProjectDataService goViewProjectDataService;

    @Resource
    private GoViewFileService goViewFileService;


    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN', 'CUSTOMER_USER')")
    @ResponseBody
    public ResultTable list(TablePar tablepar) throws ThingsboardException {
        PageLink pageLink = createPageLink(tablepar.getLimit(), tablepar.getPage() - 1);
        PageData<?> projectPageData = goViewProjectService.findProjectByPage(getCurrentUser().getTenantId(), pageLink);
        return ResultTable.builder()
                .data(projectPageData.getData())
                .count(projectPageData.getTotalElements())
                .msg("success")
                .code(200)
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @ResponseBody
    public AjaxResult add(@RequestBody GoViewProject goviewProject) throws ThingsboardException {
        goviewProject.setState(-1);
        goviewProject.setTenantId(getCurrentUser().getTenantId());
        return AjaxResult.successData(200, goViewProjectService.saveProject(goviewProject)).put("msg", "success");
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @ResponseBody
    public AjaxResult remove(@RequestBody List<String> toDelIdList) throws ThingsboardException {
        List<UUID> idsList = toDelIdList.stream().map(
                UUID::fromString
        ).toList();
        goViewProjectService.batchRemoveProject(getCurrentUser().getTenantId(), idsList);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @ResponseBody
    public AjaxResult editSave(@RequestBody GoViewProject goviewProject) throws ThingsboardException {
        goviewProject.setTenantId(getCurrentUser().getTenantId());
        goViewProjectService.updateProject(goviewProject);
        return AjaxResult.success();
    }

    @PostMapping("/rename")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @ResponseBody
    public AjaxResult rename(@RequestBody GoViewProject goviewProject) throws ThingsboardException {
        goviewProject.setTenantId(getCurrentUser().getTenantId());
        goViewProjectService.updateProject(goviewProject);
        return AjaxResult.success();
    }

    @PutMapping("/publish")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @ResponseBody
    public AjaxResult updateVisible(@RequestBody GoViewProject goviewProject) throws ThingsboardException {
        goviewProject.setTenantId(getCurrentUser().getTenantId());
        if(goviewProject.getState()==-1||goviewProject.getState()==1) {
            goViewProjectService.updateProject(goviewProject);
            return AjaxResult.success();
        }
        return AjaxResult.error("警告非法字段");
    }

    @GetMapping("/getData")
    @ResponseBody
    public AjaxResult getData(String projectId) throws ThingsboardException {
        GoViewProjectId goViewProjectId = new GoViewProjectId(toUUID(projectId));
        GoViewProject goviewProject= goViewProjectService.findProject(getTenantId(), goViewProjectId);
        GoViewProjectData blogText = goViewProjectDataService.findProjectDataByProjectId(getTenantId(), goViewProjectId);
        if(blogText != null) {
            GoViewProjectVo goviewProjectVo = new GoViewProjectVo();
            BeanUtils.copyProperties(goviewProject,goviewProjectVo);
            goviewProjectVo.setId(goviewProject.getId());
            goviewProjectVo.setContent(blogText.getContent());
            return AjaxResult.successData(200,goviewProjectVo).put("msg","获取成功");
        }
        return AjaxResult.successData(200, null).put("msg","无数据");
    }

    @PostMapping("/save/data")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @ResponseBody
    public AjaxResult saveData(@RequestBody GoViewProjectData data) throws ThingsboardException {
        GoViewProject goViewProject = goViewProjectService.findProject(getCurrentUser().getTenantId(), data.getProjectId());
        if (goViewProject == null) {
            return AjaxResult.error("ID不存在");
        }
        GoViewProjectData goViewProjectData = goViewProjectDataService.findProjectDataByProjectId(getCurrentUser().getTenantId(), data.getProjectId());
        if (goViewProjectData != null) {
            if (data.getContent() != null) {
                goViewProjectData.setContent(data.getContent());
            }
            if (data.getProjectId()  != null) {
                goViewProjectData.setProjectId(data.getProjectId());
            }

            goViewProjectDataService.updateProjectData(goViewProjectData);
        } else {
            data.setTenantId(getTenantId());
            goViewProjectDataService.saveProjectData(data);
        }
        return AjaxResult.success();
    }

    @PostMapping("/upload/{projectId}")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    public AjaxResult upload(@RequestBody MultipartFile object, @PathVariable String projectId) throws IOException, ThingsboardException {
        String fileName = object.getOriginalFilename();
        String suffixName = ".png";
        Long filesize = object.getSize();
        String mediaKey, fileSuffixName = "";
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            mediaKey = UUID.randomUUID().toString().replace("-", "");
            fileSuffixName = mediaKey + suffixName;
        }
        GoViewFile goViewFile = new GoViewFile();
        goViewFile.setTenantId(getCurrentUser().getTenantId());
        goViewFile.setName(fileSuffixName);
        goViewFile.setSize(Integer.parseInt(filesize + ""));
        goViewFile.setSuffix(suffixName);
        goViewFile.setData(object.getBytes());
        goViewFileService.saveFile(getCurrentUser().getTenantId(), projectId, goViewFile);
        GoViewFileVo goViewFileVo = new GoViewFileVo();
        BeanUtils.copyProperties(goViewFile, goViewFileVo);
        goViewFileVo.setFileName(fileSuffixName);
        return AjaxResult.successData(200, goViewFileVo);
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        // 1. 根据文件名查询文件信息
        GoViewFile goViewFile = goViewFileService.findFileByNameAnonymous(fileName);

        // 2. 文件不存在或数据为空时返回 404
        if (goViewFile == null || goViewFile.getData() == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. 构建响应头（设置 contentType 和内容长度）
        HttpHeaders headers = new HttpHeaders();
        // 拼接正确的媒体类型，例如 image/png、image/jpg
        String mediaType = "image/" + goViewFile.getSuffix().substring(1);
        headers.setContentType(MediaType.parseMediaType(mediaType));
        headers.setContentLength(goViewFile.getData().length);

        // 4. 返回 ResponseEntity，包含图片字节数组、响应头和 200 状态码
        return new ResponseEntity<>(goViewFile.getData(), headers, HttpStatus.OK);
    }
}

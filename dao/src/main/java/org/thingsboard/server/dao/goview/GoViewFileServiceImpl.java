package org.thingsboard.server.dao.goview;

import com.google.common.util.concurrent.FluentFuture;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.goview.GoViewFile;
import org.thingsboard.server.common.data.goview.GoViewProject;
import org.thingsboard.server.common.data.id.*;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.entity.AbstractEntityService;

import java.util.Optional;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

@Slf4j
@Service
@NoArgsConstructor
public class GoViewFileServiceImpl extends AbstractEntityService implements GoViewFileService {

    @Resource
    private GoViewFileDao goViewFileDao;

    @Resource
    private GoViewProjectService  goViewProjectService;

    @Override
    public PageData<GoViewFile> findFileByPage(TenantId tenantId, PageLink pageLink) {
        log.trace("Executing findFileByPage [{}]", tenantId);
        return goViewFileDao.findByTenantId(tenantId.getId(), pageLink);
    }

    @Transactional
    @Override
    public void saveFile(TenantId tenantId, String projectId, GoViewFile file) {
        try {
            log.trace("Executing saveFile [{}] [{}]", tenantId, file);
            goViewFileDao.save(tenantId, file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteFile(TenantId tenantId, GoViewFileId fileId) {
        try {
            log.trace("Executing deleteFile [{}] [{}]", tenantId, fileId);
            goViewFileDao.removeById(tenantId, fileId.getId());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean updateFile(TenantId tenantId, GoViewFile file) {
        try {
            log.trace("Executing updateFile [{}] [{}]", tenantId, file);
            GoViewFile existFile = findFile(tenantId, file.getId());
            if (existFile == null) {
                return false;
            }
            goViewFileDao.save(tenantId, file);
            return true;
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public GoViewFile findFile(TenantId tenantId, GoViewFileId fileId) {
        log.trace("Executing findFile [{}] [{}]", tenantId, fileId);
        return goViewFileDao.findById(tenantId, fileId.getId());
    }

    @Override
    public GoViewFile findFileByName(TenantId tenantId, String fileName) {
        log.trace("Executing findFileByName [{}] [{}]", tenantId, fileName);
        return goViewFileDao.findByTenantIdAndName(tenantId, fileName).orElseThrow(
                () -> new RuntimeException("File with name " + fileName + " not found")
        );
    }

    @Override
    public GoViewFile findFileByNameAnonymous(String fileName) {
        log.trace("Executing findFileByNameAnonymous [{}]", fileName);
        return goViewFileDao.findByName(fileName).orElseThrow(
                () -> new RuntimeException("File with name " + fileName + " not found")
        );
    }

    @Override
    public Optional<HasId<?>> findEntity(TenantId tenantId, EntityId entityId) {
        return Optional.ofNullable(findFile(tenantId, new GoViewFileId(entityId.getId())));
    }

    @Override
    public FluentFuture<Optional<HasId<?>>> findEntityAsync(TenantId tenantId, EntityId entityId) {
        return FluentFuture.from(goViewFileDao.findByIdAsync(tenantId, entityId.getId()))
                .transform(Optional::ofNullable, directExecutor());
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GOVIEWFILE;
    }
}
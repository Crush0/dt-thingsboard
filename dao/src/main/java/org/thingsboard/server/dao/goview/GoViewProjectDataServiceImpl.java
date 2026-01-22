package org.thingsboard.server.dao.goview;

import com.google.common.util.concurrent.FluentFuture;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.goview.GoViewProjectData;
import org.thingsboard.server.common.data.id.*;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.entity.AbstractEntityService;

import java.util.Optional;

@Slf4j
@Service
@NoArgsConstructor
public class GoViewProjectDataServiceImpl extends AbstractEntityService implements GoViewProjectDataService {

    @Resource
    private GoViewProjectDataDao goViewProjectDataDao;

    @Override
    public PageData<GoViewProjectData> findProjectDataByPage(TenantId tenantId, PageLink pageLink) {
        log.trace("Executing findProjectDataByPage [{}]", tenantId);
        return goViewProjectDataDao.findByTenantId(tenantId, pageLink);
    }

    @Override
    public void saveProjectData(GoViewProjectData projectData) {
        try {
            log.trace("Executing saveProjectData [{}]",projectData);
            goViewProjectDataDao.save(projectData.getTenantId(), projectData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteProjectData(TenantId tenantId, GoViewProjectDataId projectDataId) {
        try {
            log.trace("Executing deleteProjectData [{}] [{}]", tenantId, projectDataId);
            goViewProjectDataDao.removeById(tenantId, projectDataId.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateProjectData(GoViewProjectData projectData) {
        try {
            log.trace("Executing updateProjectData [{}]", projectData);
            GoViewProjectData existProjectData = findProjectData(projectData.getTenantId(), projectData.getId());
            if (existProjectData == null) {
                return;
            }
            goViewProjectDataDao.save(projectData.getTenantId(), projectData);
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public GoViewProjectData findProjectData(TenantId tenantId, GoViewProjectDataId projectDataId) {
        log.trace("Executing findProjectData [{}] [{}]", tenantId, projectDataId);
        return goViewProjectDataDao.findById(tenantId, projectDataId.getId());
    }

    @Override
    public GoViewProjectData findProjectDataByProjectId(TenantId tenantId, GoViewProjectId projectId) {
        log.trace("Executing findProjectDataByProjectId [{}] [{}]", tenantId, projectId);
        return goViewProjectDataDao.findByTenantIdAndProjectId(tenantId, projectId).orElse(null);
    }

    @Override
    public Optional<HasId<?>> findEntity(TenantId tenantId, EntityId entityId) {
        return Optional.ofNullable(findProjectData(tenantId, new GoViewProjectDataId(entityId.getId())));
    }

    @Override
    public FluentFuture<Optional<HasId<?>>> findEntityAsync(TenantId tenantId, EntityId entityId) {
        return null;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GOVIEWPROJECTDATA;
    }
}
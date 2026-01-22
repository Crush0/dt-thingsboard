package org.thingsboard.server.dao.goview;

import com.google.common.util.concurrent.FluentFuture;
import jakarta.annotation.Resource;
import jakarta.validation.ValidationException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.goview.GoViewProject;
import org.thingsboard.server.common.data.id.*;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.entity.AbstractEntityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

@Slf4j
@Service
@NoArgsConstructor
public class GoViewProjectServiceImpl extends AbstractEntityService implements GoViewProjectService {

    @Resource
    private GoViewProjectDao goViewProjectDao;

    @Override
    public PageData<GoViewProject> findProjectByPage(TenantId tenantId, PageLink pageLink) {
        log.trace("Executing findProjectByPage [{}]", tenantId);
        return goViewProjectDao.findByTenantId(tenantId, pageLink);
    }

    @Override
    public GoViewProject saveProject(GoViewProject project) {
        try {
            log.trace("Executing saveProject [{}]", project);
            return goViewProjectDao.save(project.getTenantId(), project);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProject(TenantId tenantId, UUID projectId) {
        try {
            log.trace("Executing deleteProject [{}] [{}]", tenantId, projectId);
            goViewProjectDao.removeById(tenantId, projectId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean batchRemoveProject(TenantId tenantId, List<UUID> projectIds) {
        try {
            log.trace("Executing deleteProject [{}] [{}]", tenantId, projectIds);
            goViewProjectDao.removeAllByTenantIdAndProjectIds(tenantId, projectIds);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean updateProject(GoViewProject project) {
        try {
            if (project.getState() != 1 && project.getState() != -1) {
                throw new ValidationException("Project's state must be -1 or 1");
            }
            log.trace("Executing updateProject [{}]", project);
            GoViewProject existProject = findProject(project.getTenantId(), project.getId());
            if (existProject == null) {
                return false;
            }
            if (project.getIndexImage() == null) {
                project.setIndexImage(existProject.getIndexImage());
            }
            if (project.getState() == null) {
                project.setState(existProject.getState());
            }
            if (project.getRemarks() == null) {
                project.setRemarks(existProject.getRemarks());
            }
            if (project.getName() == null) {
                project.setName(existProject.getName());
            }
            goViewProjectDao.save(project.getTenantId(), project);
            return true;
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public GoViewProject findProject(TenantId tenantId, GoViewProjectId projectId) {
        log.trace("Executing findDomainInfo [{}] [{}]", tenantId, projectId);
        return goViewProjectDao.findById(tenantId, projectId.getId());
    }

    @Override
    public Optional<HasId<?>> findEntity(TenantId tenantId, EntityId entityId) {
        return Optional.ofNullable(findProject(tenantId, new GoViewProjectId(entityId.getId())));
    }

    @Override
    public FluentFuture<Optional<HasId<?>>> findEntityAsync(TenantId tenantId, EntityId entityId) {
        return FluentFuture.from(goViewProjectDao.findByIdAsync(tenantId, entityId.getId()))
                .transform(Optional::ofNullable, directExecutor());
    }

    @Override
    public void deleteEntity(TenantId tenantId, EntityId id, boolean force) {
        deleteProject(tenantId, id.getId());
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GOVIEWPROJECT;
    }
}

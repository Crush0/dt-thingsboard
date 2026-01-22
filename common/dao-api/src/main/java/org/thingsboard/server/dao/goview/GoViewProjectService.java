package org.thingsboard.server.dao.goview;

import org.thingsboard.server.common.data.goview.GoViewProject;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.entity.EntityDaoService;

import java.util.List;
import java.util.UUID;


public interface GoViewProjectService extends EntityDaoService {
    PageData<GoViewProject> findProjectByPage(TenantId tenantId, PageLink pageLink);
    GoViewProject saveProject(GoViewProject project);
    boolean deleteProject(TenantId tenantId, UUID projectId);
    boolean updateProject(GoViewProject project);
    GoViewProject findProject(TenantId tenantId, GoViewProjectId projectId);
    boolean batchRemoveProject(TenantId tenantId, List<UUID> projectIds);
}

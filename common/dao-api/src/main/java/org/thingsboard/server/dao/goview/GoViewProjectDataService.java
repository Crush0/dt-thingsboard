package org.thingsboard.server.dao.goview;

import org.thingsboard.server.common.data.goview.GoViewProjectData;
import org.thingsboard.server.common.data.id.GoViewProjectDataId;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.entity.EntityDaoService;

public interface GoViewProjectDataService extends EntityDaoService {
    PageData<GoViewProjectData> findProjectDataByPage(TenantId tenantId, PageLink pageLink);
    void saveProjectData(GoViewProjectData projectData);
    void deleteProjectData(TenantId tenantId, GoViewProjectDataId projectDataId);
    void updateProjectData(GoViewProjectData projectData);
    GoViewProjectData findProjectData(TenantId tenantId, GoViewProjectDataId projectDataId);
    GoViewProjectData findProjectDataByProjectId(TenantId tenantId, GoViewProjectId projectId);
}
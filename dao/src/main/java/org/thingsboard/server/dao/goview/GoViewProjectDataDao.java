package org.thingsboard.server.dao.goview;

import org.thingsboard.server.common.data.goview.GoViewProjectData;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.Dao;

import java.util.Optional;

public interface GoViewProjectDataDao extends Dao<GoViewProjectData> {
    PageData<GoViewProjectData> findByTenantId(TenantId tenantId, PageLink pageLink);
    Optional<GoViewProjectData> findByTenantIdAndProjectId(TenantId tenantId, GoViewProjectId projectId);
}
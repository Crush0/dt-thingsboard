package org.thingsboard.server.dao.goview;

import org.thingsboard.server.common.data.goview.GoViewProject;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.Dao;

import java.util.Collection;
import java.util.UUID;

public interface GoViewProjectDao extends Dao<GoViewProject> {
    PageData<GoViewProject> findByTenantId(TenantId tenantId, PageLink pageLink);
    void removeAllByTenantIdAndProjectIds(TenantId tenantId, Collection<UUID> idsList);
}

package org.thingsboard.server.dao.goview;

import org.thingsboard.server.common.data.goview.GoViewFile;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.Dao;

import java.util.Optional;
import java.util.UUID;

public interface GoViewFileDao extends Dao<GoViewFile> {
    PageData<GoViewFile> findByTenantId(UUID tenantId, PageLink pageLink);
    Optional<GoViewFile> findByTenantIdAndName(TenantId tenantId, String fileName);
    Optional<GoViewFile> findByName(String fileName);
    void deleteByName(String fileName);
}
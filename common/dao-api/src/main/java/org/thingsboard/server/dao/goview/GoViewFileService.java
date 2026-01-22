package org.thingsboard.server.dao.goview;

import org.thingsboard.server.common.data.goview.GoViewFile;
import org.thingsboard.server.common.data.id.GoViewFileId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.entity.EntityDaoService;

public interface GoViewFileService extends EntityDaoService {
    PageData<GoViewFile> findFileByPage(TenantId tenantId, PageLink pageLink);
    void saveFile(TenantId tenantId, String projectId, GoViewFile file);
    boolean deleteFile(TenantId tenantId, GoViewFileId fileId);
    boolean updateFile(TenantId tenantId, GoViewFile file);
    GoViewFile findFile(TenantId tenantId, GoViewFileId fileId);
    GoViewFile findFileByName(TenantId tenantId, String fileName);
    GoViewFile findFileByNameAnonymous(String fileName);
}
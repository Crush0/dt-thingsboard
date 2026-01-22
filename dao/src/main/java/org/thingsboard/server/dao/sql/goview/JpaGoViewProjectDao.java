package org.thingsboard.server.dao.sql.goview;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.goview.GoViewProject;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.goview.GoViewProjectDao;
import org.thingsboard.server.dao.model.sql.GoViewProjectEntity;
import org.thingsboard.server.dao.sql.JpaAbstractDao;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.Collection;
import java.util.UUID;

@SqlDao
@Component
@Slf4j
public class JpaGoViewProjectDao extends JpaAbstractDao<GoViewProjectEntity, GoViewProject> implements GoViewProjectDao {

    @Resource
    private GoViewProjectRepository goViewProjectRepository;

    @Override
    public PageData<GoViewProject> findByTenantId(TenantId tenantId, PageLink pageLink) {
        return DaoUtil.toPageData(
                goViewProjectRepository.findAllByTenantId(tenantId.getId(), DaoUtil.toPageable(pageLink))
        );
    }

    @Override
    public void removeAllByTenantIdAndProjectIds(TenantId tenantId, Collection<UUID> idsList) {
        goViewProjectRepository.removeAllByTenantIdAndIdIn(tenantId.getId(), idsList);
    }

    @Override
    protected Class<GoViewProjectEntity> getEntityClass() {
        return GoViewProjectEntity.class;
    }

    @Override
    protected JpaRepository<GoViewProjectEntity, UUID> getRepository() {
        return goViewProjectRepository;
    }
}

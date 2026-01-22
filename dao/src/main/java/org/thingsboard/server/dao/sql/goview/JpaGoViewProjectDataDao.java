package org.thingsboard.server.dao.sql.goview;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.goview.GoViewProjectData;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.goview.GoViewProjectDataDao;
import org.thingsboard.server.dao.model.sql.GoViewProjectDataEntity;
import org.thingsboard.server.dao.sql.JpaAbstractDao;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.Optional;
import java.util.UUID;

@SqlDao
@Component
@Slf4j
public class JpaGoViewProjectDataDao extends JpaAbstractDao<GoViewProjectDataEntity, GoViewProjectData> implements GoViewProjectDataDao {
    @Resource
    private GoViewProjectDataRepository goViewProjectDataRepository;

    @Override
    public PageData<GoViewProjectData> findByTenantId(TenantId tenantId, PageLink pageLink) {
        return DaoUtil.toPageData(
                goViewProjectDataRepository.findAllByTenantId(tenantId.getId(), DaoUtil.toPageable(pageLink))
        );
    }

    @Override
    public Optional<GoViewProjectData> findByTenantIdAndProjectId(TenantId tenantId, GoViewProjectId projectId) {
        return Optional.ofNullable(DaoUtil.getData(goViewProjectDataRepository.findByTenantIdAndProjectId(tenantId.getId(), projectId.getId())));
    }

    @Override
    protected Class<GoViewProjectDataEntity> getEntityClass() {
        return GoViewProjectDataEntity.class;
    }

    @Override
    protected JpaRepository<GoViewProjectDataEntity, UUID> getRepository() {
        return goViewProjectDataRepository;
    }
}

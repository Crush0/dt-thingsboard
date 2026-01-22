package org.thingsboard.server.dao.sql.goview;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.goview.GoViewFile;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.goview.GoViewFileDao;
import org.thingsboard.server.dao.model.sql.GoViewFileEntity;
import org.thingsboard.server.dao.sql.JpaAbstractDao;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.Optional;
import java.util.UUID;

@SqlDao
@Slf4j
@Component
public class JpaGoViewFileDao extends JpaAbstractDao<GoViewFileEntity, GoViewFile> implements GoViewFileDao {

    @Resource
    private GoViewFileRepository goViewFileRepository;

    @Override
    public PageData<GoViewFile> findByTenantId(UUID tenantId, PageLink pageLink) {
        return DaoUtil.toPageData(
                goViewFileRepository.findGoViewFileEntitiesById(
                        tenantId,
                        pageLink.getTextSearch(),
                        DaoUtil.toPageable(pageLink)));
    }

    @Override
    public Optional<GoViewFile> findByTenantIdAndName(TenantId tenantId, String fileName) {
        return Optional.ofNullable(DaoUtil.getData(goViewFileRepository.findByTenantIdAndName(tenantId.getId(), fileName)));
    }

    @Override
    public Optional<GoViewFile> findByName(String fileName) {
        return Optional.ofNullable(DaoUtil.getData(goViewFileRepository.findByName((fileName))));
    }

    @Override
    public void deleteByName(String fileName) {
        goViewFileRepository.deleteByName(fileName);
    }

    @Override
    protected Class<GoViewFileEntity> getEntityClass() {
        return GoViewFileEntity.class;
    }

    @Override
    protected JpaRepository<GoViewFileEntity, UUID> getRepository() {
        return goViewFileRepository;
    }
}

package org.thingsboard.server.dao.sql.goview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thingsboard.server.dao.model.sql.GoViewProjectEntity;

import java.util.Collection;
import java.util.UUID;

public interface GoViewProjectRepository extends JpaRepository<GoViewProjectEntity, UUID> {
    Page<GoViewProjectEntity> findAllByTenantId(UUID tenantId, Pageable pageable);

    void removeAllByTenantIdAndIdIn(UUID tenantId, Collection<UUID> ids);
}

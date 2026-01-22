package org.thingsboard.server.dao.sql.goview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thingsboard.server.dao.model.sql.GoViewProjectDataEntity;

import java.util.UUID;

public interface GoViewProjectDataRepository extends JpaRepository<GoViewProjectDataEntity, UUID> {

    Page<GoViewProjectDataEntity> findAllByTenantId(UUID tenantId, Pageable pageable);

    GoViewProjectDataEntity findByTenantIdAndProjectId(UUID tenantId, UUID projectId);
}

package org.thingsboard.server.dao.sql.goview;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.thingsboard.server.dao.model.sql.GoViewFileEntity;

import java.util.UUID;

public interface GoViewFileRepository extends JpaRepository<GoViewFileEntity, UUID> {

    @Query("SELECT p FROM GoViewFileEntity p WHERE p.tenantId = :tenantId " +
            "AND (:textSearch IS NULL OR ilike(p.name, CONCAT('%', :textSearch, '%')) = true)")
    Page<GoViewFileEntity> findGoViewFileEntitiesById(UUID tenantId, String textSearch, Pageable pageable);

    GoViewFileEntity findByTenantIdAndName(UUID tenantId, String name);

    GoViewFileEntity findByName(String name);

    void deleteByName(String name);
}

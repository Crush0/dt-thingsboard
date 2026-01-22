package org.thingsboard.server.dao.model.sql;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thingsboard.server.common.data.goview.GoViewProjectData;
import org.thingsboard.server.common.data.id.GoViewProjectDataId;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JsonConverter;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.GOVIEW_PROJECT_DATA_TABLE_NAME)
public class GoViewProjectDataEntity extends BaseSqlEntity<GoViewProjectData> {
    @Column(name = ModelConstants.TENANT_ID_PROPERTY, nullable = false)
    private UUID tenantId;
    @Column(name = ModelConstants.GOVIEW_PROJECT_ID, nullable = false)
    private UUID projectId;
    @Convert(converter = JsonConverter.class)
    @Column(name = ModelConstants.GOVIEW_CONTENT)
    private JsonNode content;

    public GoViewProjectDataEntity(GoViewProjectData goViewProjectData) {
        super(goViewProjectData);
        if (goViewProjectData.getTenantId() != null) {
            this.tenantId = goViewProjectData.getTenantId().getId();
        }
        this.projectId = goViewProjectData.getProjectId().getId();
        this.content = goViewProjectData.getContent();
    }

    public GoViewProjectDataEntity() {
        super();
    }

    @Override
    public GoViewProjectData toData() {
        GoViewProjectData goViewProjectData = new GoViewProjectData();
        goViewProjectData.setId(new GoViewProjectDataId(id));
        if (tenantId != null) {
            goViewProjectData.setTenantId(TenantId.fromUUID(tenantId));
        }
        GoViewProjectId goViewProjectId = new GoViewProjectId(projectId);
        goViewProjectData.setProjectId(goViewProjectId);
        goViewProjectData.setContent(content);
        return goViewProjectData;
    }
}

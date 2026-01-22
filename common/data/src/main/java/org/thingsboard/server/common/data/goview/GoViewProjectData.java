package org.thingsboard.server.common.data.goview;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.thingsboard.server.common.data.BaseData;
import org.thingsboard.server.common.data.HasTenantId;
import org.thingsboard.server.common.data.id.GoViewProjectDataId;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class GoViewProjectData extends BaseData<GoViewProjectDataId> implements HasTenantId {
    @Schema(description = "JSON object with Tenant Id")
    private TenantId tenantId;

    private GoViewProjectId projectId;

    private JsonNode content;

    public GoViewProjectData() {
        super();
    }

    public GoViewProjectData(GoViewProjectDataId id) {
        super(id);
    }

    public GoViewProjectData(GoViewProjectData goViewProjectData) {
        super(goViewProjectData);
        this.tenantId = goViewProjectData.tenantId;
        this.projectId = goViewProjectData.projectId;
        this.content = goViewProjectData.content;
    }
}

package org.thingsboard.server.common.data.goview;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.thingsboard.server.common.data.BaseData;
import org.thingsboard.server.common.data.HasName;
import org.thingsboard.server.common.data.HasTenantId;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.validation.Length;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class GoViewProject extends BaseData<GoViewProjectId> implements HasTenantId, HasName {
    @Schema(description = "JSON object with Tenant Id")
    private TenantId tenantId;

    @NotBlank
    @Length(fieldName = "name")
    private String name;

    private Integer state;

    private String indexImage;

    private String remarks;

    public GoViewProject() {
        super();
    }

    public GoViewProject(GoViewProjectId id) {
        super(id);
    }

    public GoViewProject(GoViewProject goViewProject) {
        super(goViewProject);
        this.tenantId = goViewProject.tenantId;
        this.name = goViewProject.name;
        this.indexImage = goViewProject.indexImage;
        this.remarks = goViewProject.remarks;
        this.state = goViewProject.state;
    }
}

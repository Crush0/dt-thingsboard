package org.thingsboard.server.dao.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thingsboard.server.common.data.goview.GoViewProject;
import org.thingsboard.server.common.data.id.GoViewProjectId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.GOVIEW_PROJECT_TABLE_NAME)
public class GoViewProjectEntity extends BaseSqlEntity<GoViewProject> {
    @Column(name = ModelConstants.TENANT_ID_PROPERTY, nullable = false)
    private UUID tenantId;
    @Column(name = ModelConstants.NAME_PROPERTY, nullable = false)
    private String name;

    @Column(name = ModelConstants.STATE_PROPERTY, nullable = false)
    private Integer state;

    @Column(name = ModelConstants.GOVIEW_INDEX_IMAGE)
    private String indexImage;

    @Column(name = ModelConstants.GOVIEW_REMARKS)
    private String remarks;

    public GoViewProjectEntity(GoViewProject goViewProject) {
        super(goViewProject);
        if (goViewProject.getTenantId() != null) {
            this.tenantId = goViewProject.getTenantId().getId();
        }
        this.name = goViewProject.getName();
        this.state = goViewProject.getState();
        this.indexImage = goViewProject.getIndexImage();
        this.remarks = goViewProject.getRemarks();
    }

    public GoViewProjectEntity() {
        super();
    }

    @Override
    public GoViewProject toData() {
        GoViewProject goViewProject = new GoViewProject();
        goViewProject.setId(new GoViewProjectId(id));
        if (tenantId != null) {
            goViewProject.setTenantId(TenantId.fromUUID(tenantId));
        }
        goViewProject.setName(name);
        goViewProject.setState(state);
        goViewProject.setIndexImage(indexImage);
        goViewProject.setRemarks(remarks);
        return goViewProject;
    }
}

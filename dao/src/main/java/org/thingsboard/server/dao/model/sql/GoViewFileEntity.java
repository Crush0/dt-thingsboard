package org.thingsboard.server.dao.model.sql;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.thingsboard.server.common.data.goview.GoViewFile;
import org.thingsboard.server.common.data.id.GoViewFileId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = ModelConstants.GOVIEW_FILE_TABLE_NAME)
public class GoViewFileEntity extends BaseSqlEntity<GoViewFile> {
    @Column(name = ModelConstants.TENANT_ID_PROPERTY, nullable = false)
    private UUID tenantId;
    @Column(name = ModelConstants.NAME_PROPERTY, nullable = false, unique = true)
    private String name;
    @Column(name = ModelConstants.SIZE_PROPERTY, nullable = false)
    private Integer size;
    @Column(name = ModelConstants.SUFFIX_PROPERTY)
    private String suffix;

    /**
     * 虚拟路径
     */
    @Column(name = ModelConstants.GOVIEW_VIRTUAL_KEY)
    private String virtualKey;

    /**
     * 相对路径
     */
    @Column(name = ModelConstants.GOVIEW_RELATIVE_PATH)
    private String relativePath;

    @Column(name = ModelConstants.RESOURCE_DATA_COLUMN)
    private byte[] data;

    /**
     * 绝对路径
     */
    @Column(name = ModelConstants.GOVIEW_ABSOLUTE_PATH)
    private String absolutePath;

    public GoViewFileEntity(GoViewFile goViewFile) {
        super(goViewFile);
        this.absolutePath = goViewFile.getAbsolutePath();
        this.relativePath = goViewFile.getRelativePath();
        this.name = goViewFile.getName();
        this.size = goViewFile.getSize();
        this.suffix = goViewFile.getSuffix();
        this.tenantId = goViewFile.getTenantId().getId();
        this.name = goViewFile.getName();
        this.data = goViewFile.getData();
    }

    @Override
    public GoViewFile toData() {
        GoViewFile goViewFile = new GoViewFile();
        goViewFile.setId(new GoViewFileId(id));
        if (tenantId != null) {
            goViewFile.setTenantId(TenantId.fromUUID(tenantId));
        }
        goViewFile.setName(name);
        goViewFile.setSize(size);
        goViewFile.setSuffix(suffix);
        goViewFile.setVirtualKey(virtualKey);
        goViewFile.setRelativePath(relativePath);
        goViewFile.setAbsolutePath(absolutePath);
        goViewFile.setData(data);
        return goViewFile;
    }
}

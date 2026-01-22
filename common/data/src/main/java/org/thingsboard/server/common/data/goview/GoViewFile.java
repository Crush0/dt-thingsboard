package org.thingsboard.server.common.data.goview;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.thingsboard.server.common.data.BaseData;
import org.thingsboard.server.common.data.HasName;
import org.thingsboard.server.common.data.HasTenantId;
import org.thingsboard.server.common.data.id.GoViewFileId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.validation.Length;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class GoViewFile extends BaseData<GoViewFileId> implements HasTenantId, HasName {
    @Schema(description = "JSON object with Tenant Id")
    private TenantId tenantId;

    @Length(fieldName = "name")
    private String name;

    private Integer size;

    private String suffix;

    private byte[] data;
    /**
     * 虚拟路径
     */
    private String virtualKey;

    /**
     * 相对路径
     */
    private String relativePath;

    /**
     * 绝对路径
     */
    private String absolutePath;

    public GoViewFile() {
        super();
    }

    public GoViewFile(GoViewFileId id) {
        super(id);
    }

    public GoViewFile(GoViewFile goViewFile) {
        super(goViewFile);
        this.tenantId = goViewFile.tenantId;
        this.name = goViewFile.name;
        this.size = goViewFile.size;
        this.suffix = goViewFile.suffix;
        this.virtualKey = goViewFile.virtualKey;
        this.relativePath = goViewFile.relativePath;
        this.absolutePath = goViewFile.absolutePath;
        this.data = goViewFile.data;
    }
}

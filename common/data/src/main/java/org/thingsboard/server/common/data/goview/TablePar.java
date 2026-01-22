package org.thingsboard.server.common.data.goview;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema
@Data
@ToString(callSuper = true)
public class TablePar {
    @Schema(description = "页码")
    private int page;
    @Schema(description = "每页数量")
    private int limit;
    @Schema(description = "排序字段")
    private String orderByColumn;
    @Schema(description = "排序字符")
    private String isAsc;
    @Schema(description = "搜索文本")
    private String searchText;
}

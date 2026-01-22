package org.thingsboard.server.common.data.goview;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.thingsboard.server.common.data.id.GoViewProjectId;

@Data
public class GoViewProjectFullData {
    private GoViewProjectId id;

    private String name;

    private Integer state;

    private String indexImage;

    private String remarks;

    private JsonNode content;
}

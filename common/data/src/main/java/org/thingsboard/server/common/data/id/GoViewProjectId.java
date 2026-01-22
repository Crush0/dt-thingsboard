package org.thingsboard.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.thingsboard.server.common.data.EntityType;

import java.util.UUID;

public class GoViewProjectId extends UUIDBased implements EntityId {
    @JsonCreator
    public GoViewProjectId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static GoViewProjectId fromString(String goviewProjectId) {
        return new GoViewProjectId(UUID.fromString(goviewProjectId));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GOVIEWPROJECT;
    }
}

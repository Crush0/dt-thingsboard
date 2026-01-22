package org.thingsboard.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.thingsboard.server.common.data.EntityType;

import java.util.UUID;

public class GoViewProjectDataId extends UUIDBased implements EntityId {

    @JsonCreator
    public GoViewProjectDataId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static GoViewProjectDataId fromString(String goviewProjectDataId) {
        return new GoViewProjectDataId(UUID.fromString(goviewProjectDataId));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GOVIEWPROJECTDATA;
    }
}

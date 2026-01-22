package org.thingsboard.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.common.data.goview.GoViewFile;

import java.util.UUID;

public class GoViewFileId extends UUIDBased implements EntityId{
    @JsonCreator
    public GoViewFileId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static GoViewFileId fromString(String goViewFileId) {
        return new GoViewFileId(UUID.fromString(goViewFileId));
    }

    @JsonIgnore
    static final ConcurrentReferenceHashMap<UUID, GoViewFileId> goviewFiles = new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.SOFT);

    @Override
    public EntityType getEntityType() {
        return EntityType.GOVIEWFILE;
    }

    @JsonCreator
    public static GoViewFileId fromUUID(@JsonProperty("id") UUID id) {
        return goviewFiles.computeIfAbsent(id, GoViewFileId::new);
    }
}

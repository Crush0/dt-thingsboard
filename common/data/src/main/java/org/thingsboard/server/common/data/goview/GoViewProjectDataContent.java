package org.thingsboard.server.common.data.goview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.thingsboard.server.common.data.cf.configuration.ScriptCalculatedFieldConfiguration;
import org.thingsboard.server.common.data.cf.configuration.SimpleCalculatedFieldConfiguration;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleCalculatedFieldConfiguration.class, name = "SIMPLE"),
        @JsonSubTypes.Type(value = ScriptCalculatedFieldConfiguration.class, name = "SCRIPT")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GoViewProjectDataContent {
    UUID projectId();
}

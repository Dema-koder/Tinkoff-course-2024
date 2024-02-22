package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class GitHubUpdate {
    public String owner;
    @JsonProperty("name")
    public String name;
    @JsonProperty("updated_at")
    public OffsetDateTime updatedAt;

    @JsonProperty("owner")
    public void setOwner(Map<String, String> owner) {
        this.owner = owner.get("login");
    }
}

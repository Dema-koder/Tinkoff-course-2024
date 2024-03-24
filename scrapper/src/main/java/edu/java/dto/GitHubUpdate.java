package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GitHubUpdate {
    public String owner;
    @JsonProperty("name")
    public String name;
    @JsonProperty("updated_at")
    public OffsetDateTime updatedAt;
    @JsonProperty("pushed_at")
    public OffsetDateTime pushedAt;

    @JsonProperty("owner")
    public void setOwner(Map<String, String> owner) {
        this.owner = owner.get("login");
    }
}

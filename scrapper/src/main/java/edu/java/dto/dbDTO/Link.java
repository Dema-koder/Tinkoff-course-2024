package edu.java.dto.dbDTO;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Link {
    private Long id;
    private String linkName;
    private OffsetDateTime lastCheck;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCommit;
    private int amountOfIssues;
    private String type;
}

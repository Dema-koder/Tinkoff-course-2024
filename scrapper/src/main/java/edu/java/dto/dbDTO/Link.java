package edu.java.dto.dbDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Link {
    private Long id;
    private String linkName;
    private Timestamp lastUpdate;
}

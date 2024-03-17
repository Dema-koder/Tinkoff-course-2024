package edu.java.dto.dbDTO;

import java.sql.Timestamp;
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
    private Timestamp lastUpdate;
}

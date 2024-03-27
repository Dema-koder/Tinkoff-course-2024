package edu.java.dto.dbDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatToLink {
    private Long chatId;
    private Long linkId;
}

/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq.generation2.tables.pojos;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class ChatToLink implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;
    private Long linkId;

    public ChatToLink() {
    }

    public ChatToLink(ChatToLink value) {
        this.chatId = value.chatId;
        this.linkId = value.linkId;
    }

    @ConstructorProperties({"chatId", "linkId"})
    public ChatToLink(
        @NotNull Long chatId,
        @NotNull Long linkId
    ) {
        this.chatId = chatId;
        this.linkId = linkId;
    }

    /**
     * Getter for <code>CHAT_TO_LINK.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>CHAT_TO_LINK.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>CHAT_TO_LINK.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>CHAT_TO_LINK.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChatToLink other = (ChatToLink) obj;
        if (this.chatId == null) {
            if (other.chatId != null) {
                return false;
            }
        } else if (!this.chatId.equals(other.chatId)) {
            return false;
        }
        if (this.linkId == null) {
            if (other.linkId != null) {
                return false;
            }
        } else if (!this.linkId.equals(other.linkId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChatToLink (");

        sb.append(chatId);
        sb.append(", ").append(linkId);

        sb.append(")");
        return sb.toString();
    }
}

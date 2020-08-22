package lktgt.webide.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String value;

    Role(String val) {
        this.value = val;
    }

    public String getValue(){
        return value;
    }
}

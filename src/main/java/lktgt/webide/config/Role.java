package lktgt.webide.config;

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

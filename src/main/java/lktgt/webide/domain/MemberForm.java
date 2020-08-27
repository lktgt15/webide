package lktgt.webide.domain;

import javax.validation.constraints.NotEmpty;

public class MemberForm {
    private String name;
    private String pw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}

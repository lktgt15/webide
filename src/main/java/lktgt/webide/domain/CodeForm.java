package lktgt.webide.domain;

public class CodeForm {
    private String Language;
    private String Code;

    public CodeForm(){

    }

    public CodeForm(String language,String code){
        Language = language;
        Code = code;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
}

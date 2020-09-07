package lktgt.webide.domain;

import java.util.List;

public class CodeForm {
    private String Language;
    private String Code;
    private List<Long> k;
    private List<Long> min;
    private List<Long> max;

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

    public List<Long> getMax() {
        return max;
    }

    public void setMax(List<Long> max) {
        this.max = max;
    }

    public List<Long> getMin() {
        return min;
    }

    public void setMin(List<Long> min) {
        this.min = min;
    }

    public List<Long> getK() {
        return k;
    }

    public void setK(List<Long> k) {
        this.k = k;
    }
}

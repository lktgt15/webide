package lktgt.webide.domain;

import java.util.List;

public class CodeForm {
    private String Language;
    private String Code;
    private List<Long> kmin;
    private List<Long> kmax;
    private List<Long> rangemin;
    private List<Long> rangemax;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public List<Long> getKmin() {
        return kmin;
    }

    public void setKmin(List<Long> kmin) {
        this.kmin = kmin;
    }

    public List<Long> getKmax() {
        return kmax;
    }

    public void setKmax(List<Long> kmax) {
        this.kmax = kmax;
    }

    public List<Long> getRangemin() {
        return rangemin;
    }

    public void setRangemin(List<Long> rangemin) {
        this.rangemin = rangemin;
    }

    public List<Long> getRangemax() {
        return rangemax;
    }

    public void setRangemax(List<Long> rangemax) {
        this.rangemax = rangemax;
    }
}

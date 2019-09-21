package com.spring.mpvue.springbootmpvue.util;

public enum YewuEnum {
    BAOGAO_TYPE("BAOGAO_TYPE"),
    WEITUO_TYPE("WEITUO_TYPE"),
    WEITUO_COMPANY("WEITUO_COMPANY"),
    YEWUYUAN("YEWUYUAN");

    private String value;

    YewuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

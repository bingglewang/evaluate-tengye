package com.spring.mpvue.springbootmpvue.mybatis.in;

import javax.validation.constraints.NotEmpty;

public class CellSearchIn {
    @NotEmpty(message = "城市不能为空")
    private String city;
    @NotEmpty(message = "查询关键字不能为空")
    private String keyword;
    //@NotEmpty(message = "区域不能为空")
    private String district;
    private String block;
    private Double lat;
    private Double lng;
    private Integer from;
    private Integer radius;
    private Integer max;
    @NotEmpty(message = "token不能为空")
    private String token;

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

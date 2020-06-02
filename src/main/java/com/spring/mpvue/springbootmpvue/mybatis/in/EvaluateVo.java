package com.spring.mpvue.springbootmpvue.mybatis.in;

import com.spring.mpvue.springbootmpvue.util.DateUtil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName EvaluateVo
 * @Description TODO
 * @Author binggleW
 * @Date 2020-06-02 13:18
 * @Version 1.0
 **/
public class EvaluateVo {
    @NotEmpty(message = "城市不能为空")
    private String city;
    @NotEmpty(message = "区域不能为空")
    private String district;
    @NotEmpty(message = "小区不能为空")
    private String name;
    private String buildingNumber;
    private String unitNumber;
    @NotNull(message = "面积不能为空")
    private Float size;
    private Integer floor;
    private Integer maxFloor;
    private Integer room;
    private Integer hall;
    private Integer toilet;
    private String calDate = DateUtil.getYearMonthDate();
    private String propertyType;
    private String direction;
    private String decoration;
    private Integer decorationCost;
    private Integer decorationAge;
    private String view;
    private String noise;
    private Float gardenSize;
    private Float basementSize;
    private String negativeFactor;
    private Integer age;
    private Integer carportPrice;
    private String facilities;
    private String insidePosition;
    @NotEmpty(message = "token不能为空")
    private String token;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(Integer maxFloor) {
        this.maxFloor = maxFloor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getHall() {
        return hall;
    }

    public void setHall(Integer hall) {
        this.hall = hall;
    }

    public Integer getToilet() {
        return toilet;
    }

    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public Integer getDecorationCost() {
        return decorationCost;
    }

    public void setDecorationCost(Integer decorationCost) {
        this.decorationCost = decorationCost;
    }

    public Integer getDecorationAge() {
        return decorationAge;
    }

    public void setDecorationAge(Integer decorationAge) {
        this.decorationAge = decorationAge;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public Float getGardenSize() {
        return gardenSize;
    }

    public void setGardenSize(Float gardenSize) {
        this.gardenSize = gardenSize;
    }

    public Float getBasementSize() {
        return basementSize;
    }

    public void setBasementSize(Float basementSize) {
        this.basementSize = basementSize;
    }

    public String getNegativeFactor() {
        return negativeFactor;
    }

    public void setNegativeFactor(String negativeFactor) {
        this.negativeFactor = negativeFactor;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCarportPrice() {
        return carportPrice;
    }

    public void setCarportPrice(Integer carportPrice) {
        this.carportPrice = carportPrice;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getInsidePosition() {
        return insidePosition;
    }

    public void setInsidePosition(String insidePosition) {
        this.insidePosition = insidePosition;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

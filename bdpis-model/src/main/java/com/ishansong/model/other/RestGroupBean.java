package com.ishansong.model.other;

/**
 * Created by iss on 2017/9/11 下午3:05.
 * <p>
 * 对地址from_address 进行group 操作 实体类
 */
public class RestGroupBean implements  Comparable<RestGroupBean>{

    private String order_time;
    //时间差（分钟）
    private String between_minute;
    private String from_lat;
    private String from_lng;
    private String from_address;
    private Double address_cnt;
    //距离
    private Double distance;
    //权重值
    private Double weightValue;


    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getBetween_minute() {
        return between_minute;
    }

    public void setBetween_minute(String between_minute) {
        this.between_minute = between_minute;
    }

    public String getFrom_lat() {
        return from_lat;
    }

    public void setFrom_lat(String from_lat) {
        this.from_lat = from_lat;
    }

    public String getFrom_lng() {
        return from_lng;
    }

    public void setFrom_lng(String from_lng) {
        this.from_lng = from_lng;
    }


    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public Double getAddress_cnt() {
        return address_cnt;
    }

    public void setAddress_cnt(Double address_cnt) {
        this.address_cnt = address_cnt;
    }


    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Double weightValue) {
        this.weightValue = weightValue;
    }

    @Override
    public int compareTo(RestGroupBean o) {
        return o.getWeightValue().compareTo(this.getWeightValue());
    }
}

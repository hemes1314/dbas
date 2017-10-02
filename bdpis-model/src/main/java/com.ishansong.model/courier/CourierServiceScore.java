package com.ishansong.model.courier;

/**
 * Created by iss on 2017/8/21 下午4:59.
 * <p>
 * 服务分实体类
 */
public class CourierServiceScore {

    private int courier_id;
    private int city_id;
    private int region_id;
    private String region_name;
    private double service_ratio;
    private double service_score;
    private int level_before;
    private int level_new;
    private int courier_type;
    private String etl_time;

    public int getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(int courier_id) {
        this.courier_id = courier_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public double getService_ratio() {
        return service_ratio;
    }

    public void setService_ratio(double service_ratio) {
        this.service_ratio = service_ratio;
    }

    public double getService_score() {
        return service_score;
    }

    public void setService_score(double service_score) {
        this.service_score = service_score;
    }

    public int getLevel_before() {
        return level_before;
    }

    public void setLevel_before(int level_before) {
        this.level_before = level_before;
    }

    public int getLevel_new() {
        return level_new;
    }

    public void setLevel_new(int level_new) {
        this.level_new = level_new;
    }

    public int getCourier_type() {
        return courier_type;
    }

    public void setCourier_type(int courier_type) {
        this.courier_type = courier_type;
    }

    public String getEtl_time() {
        return etl_time;
    }

    public void setEtl_time(String etl_time) {
        this.etl_time = etl_time;
    }
}

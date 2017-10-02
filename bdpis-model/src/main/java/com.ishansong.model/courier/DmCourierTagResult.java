package com.ishansong.model.courier;

/**
 * Created by iss on 2017/6/23 上午10:54.
 * <p>
 * 对应数据库dm_courier_tag_all_result表
 */
public class DmCourierTagResult {

    private String courier_id;
    private String place_cnt;
    private String is_full_work;
    private String avg_speed;
    private String category;
    private String equipment;
    private String score;
    private String travel_id;
    private String travel_way;

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getPlace_cnt() {
        return place_cnt;
    }

    public void setPlace_cnt(String place_cnt) {
        this.place_cnt = place_cnt;
    }

    public String getIs_full_work() {
        return is_full_work;
    }

    public void setIs_full_work(String is_full_work) {
        this.is_full_work = is_full_work;
    }

    public String getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(String avg_speed) {
        this.avg_speed = avg_speed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(String travel_id) {
        this.travel_id = travel_id;
    }

    public String getTravel_way() {
        return travel_way;
    }

    public void setTravel_way(String travel_way) {
        this.travel_way = travel_way;
    }
}

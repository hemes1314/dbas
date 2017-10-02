package com.ishansong.model.courier;

/**
 * Created by Roger on 17/7/14.
 */
public class CourierCreditScore {
    private String courier_id;
    private String score;
    private String city_id;
    private String utime;

    public String getCourier_id() {
        return courier_id;
    }

    public String getScore() {
        return score;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}

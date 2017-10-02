package com.ishansong.model.courier;

/**
 * Created by Roger on 17/7/14.
 */
public class CourierCreditScoreNew {
    private String courier_id;
    private String city_id;
    private String credit_score;
    private String etl_time;

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(String credit_score) {
        this.credit_score = credit_score;
    }

    public String getEtl_time() {
        return etl_time;
    }

    public void setEtl_time(String etl_time) {
        this.etl_time = etl_time;
    }

}

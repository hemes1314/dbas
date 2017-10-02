package com.ishansong.model.courier;

/**
 * Created by iss on 2017/8/21 下午4:59.
 * <p>
 * 服务分实体类(要转换后的对象)
 */
public class CourierServiceScorebatch {

    private int courierId;
    private double serviceScore;


    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(double serviceScore) {
        this.serviceScore = serviceScore;
    }
}

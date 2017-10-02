package com.ishansong.model.order;

/**
 * Created by iss on 2017/6/23 上午10:54.
 * <p>
 * 对应数据库dm_order_tag_all_result 表
 */
public class DmOrderTagResult {

    private String order_id;
    private String order_number;
    private String goods;
    private String weight;
    private String distance;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

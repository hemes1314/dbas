package com.ishansong.model.goods;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by wubin on 17/9/26.
 */
public class GoodsTypePrefer implements Comparable<GoodsTypePrefer>{

    private String courierId;

    private String orderGoodsPrefer;

    private String etlTime;

    private String goodsType;

    private String prefer;

    private String coefficient;

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getOrderGoodsPrefer() {
        return orderGoodsPrefer;
    }

    public void setOrderGoodsPrefer(String orderGoodsPrefer) {
        this.orderGoodsPrefer = orderGoodsPrefer;
    }

    public String getEtlTime() {
        return etlTime;
    }

    public void setEtlTime(String etlTime) {
        this.etlTime = etlTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getPrefer() {
        return prefer;
    }

    public void setPrefer(String prefer) {
        this.prefer = prefer;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public int compareTo(GoodsTypePrefer o) {

        float prefer1 = Float.parseFloat(this.getPrefer());
        float prefer2 = Float.parseFloat(o.getPrefer());
        if(prefer1 > prefer2) {
            return -1;
        }
        if(prefer1 < prefer2) {
            return 1;
        } else {
            Integer c1 = NumberUtils.toInt(this.coefficient);
            Integer c2 = NumberUtils.toInt(o.getCoefficient());
            if(c1 > c2) {
                return -1;
            }
            if(c1 < c2) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return this.getGoodsType()+":"+this.getPrefer();
    }
}

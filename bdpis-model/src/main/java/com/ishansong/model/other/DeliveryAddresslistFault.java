package com.ishansong.model.other;

/**
 * Created by yangguoliang on 2017/9/8 下午3:19.
 * <p>
 * 常用地址推荐的返回结果对象(描述) (送件地址)
 */
public class DeliveryAddresslistFault {
    private String index;
    private String to_lat_value;
    private String to_lng_value;
    private String to_address;
    private String to_address_detail;
    private String address_cnt_value;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public String getTo_lat_value() {
        return to_lat_value;
    }

    public void setTo_lat_value(String to_lat_value) {
        this.to_lat_value = to_lat_value;
    }

    public String getTo_lng_value() {
        return to_lng_value;
    }

    public void setTo_lng_value(String to_lng_value) {
        this.to_lng_value = to_lng_value;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTo_address_detail() {
        return to_address_detail;
    }

    public void setTo_address_detail(String to_address_detail) {
        this.to_address_detail = to_address_detail;
    }

    public String getAddress_cnt_value() {
        return address_cnt_value;
    }

    public void setAddress_cnt_value(String address_cnt_value) {
        this.address_cnt_value = address_cnt_value;
    }
}

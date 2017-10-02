package com.ishansong.model.other;

/**
 * Created by yangguoliang on 2017/9/8 下午3:19.
 * <p>
 * 常用地址推荐的返回结果对象(描述) (取件地址)
 */
public class CollectAddresslistFault {
    private String index;
    private String geocode;
    private String distance;
    private String weightNumber;
    private String from_lat_value;
    private String from_lng_value;
    private String from_address;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWeightNumber() {
        return weightNumber;
    }

    public void setWeightNumber(String weightNumber) {
        this.weightNumber = weightNumber;
    }

    public String getFrom_lat_value() {
        return from_lat_value;
    }

    public void setFrom_lat_value(String from_lat_value) {
        this.from_lat_value = from_lat_value;
    }

    public String getFrom_lng_value() {
        return from_lng_value;
    }

    public void setFrom_lng_value(String from_lng_value) {
        this.from_lng_value = from_lng_value;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }
}

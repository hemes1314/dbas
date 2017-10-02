package com.ishansong.model.biordermatched;


/**
 * Created by yangguoliang on 2017/6/19
 * <p>
 * 对应的画像
 */
public class BiOrderMatched {

    private String order_id; //'订单编号'

    private String order_number; //'订单编号'

    private String weight;  //'重量'

    private String total_distance;   //'距离'

    private String goods;   //'商品'

    private String goods_dic_id;  //'品类编号'

    private String  goods_dic_type_name;   //'品类名称'

    private Integer courier_id;   //'闪送员id'

    private String city_id;  //'城市id'

    private String category_matched_val;  //'品类评分结果'

    private String category_equipment_matched_val;    //'品类-装备匹配评分'

    private String category_cr_ctg_matched_val; //'品类-品类偏好匹配评分'

    private String weight_matched_val;    //'重量评分'

    private String weight_travel_matched_val;  //'重量-交通工具评分'

    private String distance_matched_val;  //'距离评分'

    private String distance_speed_matched_val; //'距离-时效评分'

    private String distance_travel_matched_val;    //'距离-交通工具评分'

    private String remarks_matched_val;   //'要求评分'

    private String service_matched_val;   //'服务等级评分'

    private String service_credit_matched_val; //'服务等级-信誉评分'

    private String service_wktype_matched_val; //'服务等级-专、兼职评分'

    private String result_val; //'最终评分'

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(String total_distance) {
        this.total_distance = total_distance;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getGoods_dic_id() {
        return goods_dic_id;
    }

    public void setGoods_dic_id(String goods_dic_id) {
        this.goods_dic_id = goods_dic_id;
    }

    public String getGoods_dic_type_name() {
        return goods_dic_type_name;
    }

    public void setGoods_dic_type_name(String goods_dic_type_name) {
        this.goods_dic_type_name = goods_dic_type_name;
    }

    public Integer getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(Integer courier_id) {
        this.courier_id = courier_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCategory_matched_val() {
        return category_matched_val;
    }

    public void setCategory_matched_val(String category_matched_val) {
        this.category_matched_val = category_matched_val;
    }

    public String getCategory_equipment_matched_val() {
        return category_equipment_matched_val;
    }

    public void setCategory_equipment_matched_val(String category_equipment_matched_val) {
        this.category_equipment_matched_val = category_equipment_matched_val;
    }

    public String getCategory_cr_ctg_matched_val() {
        return category_cr_ctg_matched_val;
    }

    public void setCategory_cr_ctg_matched_val(String category_cr_ctg_matched_val) {
        this.category_cr_ctg_matched_val = category_cr_ctg_matched_val;
    }

    public String getWeight_matched_val() {
        return weight_matched_val;
    }

    public void setWeight_matched_val(String weight_matched_val) {
        this.weight_matched_val = weight_matched_val;
    }

    public String getWeight_travel_matched_val() {
        return weight_travel_matched_val;
    }

    public void setWeight_travel_matched_val(String weight_travel_matched_val) {
        this.weight_travel_matched_val = weight_travel_matched_val;
    }

    public String getDistance_matched_val() {
        return distance_matched_val;
    }

    public void setDistance_matched_val(String distance_matched_val) {
        this.distance_matched_val = distance_matched_val;
    }

    public String getDistance_speed_matched_val() {
        return distance_speed_matched_val;
    }

    public void setDistance_speed_matched_val(String distance_speed_matched_val) {
        this.distance_speed_matched_val = distance_speed_matched_val;
    }

    public String getDistance_travel_matched_val() {
        return distance_travel_matched_val;
    }

    public void setDistance_travel_matched_val(String distance_travel_matched_val) {
        this.distance_travel_matched_val = distance_travel_matched_val;
    }

    public String getRemarks_matched_val() {
        return remarks_matched_val;
    }

    public void setRemarks_matched_val(String remarks_matched_val) {
        this.remarks_matched_val = remarks_matched_val;
    }

    public String getService_matched_val() {
        return service_matched_val;
    }

    public void setService_matched_val(String service_matched_val) {
        this.service_matched_val = service_matched_val;
    }

    public String getService_credit_matched_val() {
        return service_credit_matched_val;
    }

    public void setService_credit_matched_val(String service_credit_matched_val) {
        this.service_credit_matched_val = service_credit_matched_val;
    }

    public String getService_wktype_matched_val() {
        return service_wktype_matched_val;
    }

    public void setService_wktype_matched_val(String service_wktype_matched_val) {
        this.service_wktype_matched_val = service_wktype_matched_val;
    }

    public String getResult_val() {
        return result_val;
    }

    public void setResult_val(String result_val) {
        this.result_val = result_val;
    }

    //    private String orderId; //'订单编号'
//
//    private String orderNumber; //'订单编号'
//
//    private String weight;  //'重量'
//
//    private String totalDistance;   //'距离'
//
//    private String goods;   //'商品'
//
//    private String goodsDicId;  //'品类编号'
//
//    private String  goodsDicTypeName;   //'品类名称'
//
//    private Integer courier_id;   //'闪送员id'
//
//    private String cityId;  //'城市id'
//
//    private String categoryMatchedVal;  //'品类评分结果'
//
//    private String categoryEquipmentMatched_val;    //'品类-装备匹配评分'
//
//    private String categoryCrCtgMatchedVal; //'品类-品类偏好匹配评分'
//
//    private String weightMatchedVal;    //'重量评分'
//
//    private String weightTravelMatchedVal;  //'重量-交通工具评分'
//
//    private String distanceMatchedVal;  //'距离评分'
//
//    private String distanceSpeedMatchedVal; //'距离-时效评分'
//
//    private String distanceTravelMatchedVal;    //'距离-交通工具评分'
//
//    private String remarksMatchedVal;   //'要求评分'
//
//    private String serviceMatchedVal;   //'服务等级评分'
//
//    private String serviceCreditMatchedVal; //'服务等级-信誉评分'
//
//    private String serviceWktypeMatchedVal; //'服务等级-专、兼职评分'
//
//    private String resultVal; //'最终评分'


}

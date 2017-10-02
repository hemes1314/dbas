package com.ishansong.model.goods;

/**
 * Created by yangguoliang on 2017/9/26 下午2:41.
 * <p>
 * 闪送员常用物品-配送熟练度
 */
public class GoodsProficiency {

    private String courier_id;
    private String goods_tags;
    private String etl_time;

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getGoods_tags() {
        return goods_tags;
    }

    public void setGoods_tags(String goods_tags) {
        this.goods_tags = goods_tags;
    }

    public String getEtl_time() {
        return etl_time;
    }

    public void setEtl_time(String etl_time) {
        this.etl_time = etl_time;
    }
}

package com.ishansong.model.other;

/**
 * Created by yangguoliang on 2017/9/27 下午2:39.
 * <p>
 * 闪送员配送物品熟练度结果实体类
 */
public class CollectGoodsProficiencyListFault {
    private String goodsSubtype;
    private String proficiency;

    public String getGoodsSubtype() {
        return goodsSubtype;
    }

    public void setGoodsSubtype(String goodsSubtype) {
        this.goodsSubtype = goodsSubtype;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}

package com.ishansong.dao.goods;

import com.ishansong.model.goods.GoodsProficiency;

import java.util.List;

/**
 * Created by yangguoliang on 2017/9/26 下午2:40.
 * <p>
 * 闪送员常用物品-配送熟练度
 */
public interface GoodsProficiencyMapper {

    public List<GoodsProficiency> queryByCourierId(String courierId) ;

}

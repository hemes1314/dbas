package com.ishansong.dao.goods;

import com.ishansong.model.courier.CourierCreditScore;
import com.ishansong.model.goods.GoodsTypePrefer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wubin on 2017/9/26
 * <p>
 * 闪送员常用物品-商品分类优先级
 */
@Repository
public interface GoodsTypePreferMapper {

    public List<GoodsTypePrefer> queryByCourierId(String courierId);
}

package com.ishansong.service.goods;

import com.ishansong.model.goods.GoodsTypePrefer;

import java.util.List;
import java.util.Map;

/**
 * Created by wubin on 2017/9/26
 * <p>
 * 商品分类
 */
public interface GoodsTypeService {

    public List<GoodsTypePrefer> queryPreferByCourierId(String courierId, Map<String,String> groupBysubTypeNameMap);

}

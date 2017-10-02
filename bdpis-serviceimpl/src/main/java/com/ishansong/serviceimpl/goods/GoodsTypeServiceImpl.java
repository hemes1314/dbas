package com.ishansong.serviceimpl.goods;

import com.ishansong.dao.goods.GoodsTypePreferMapper;
import com.ishansong.model.goods.GoodsTypePrefer;
import com.ishansong.service.goods.GoodsTypeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wubin on 2017/9/26.
 * <p>
 * (描述)
 */
@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Autowired
    private GoodsTypePreferMapper goodsTypePreferMapper;

    @Override
    public List<GoodsTypePrefer> queryPreferByCourierId(String courierId, Map<String,String> groupBysubTypeNameMap) {

        List<GoodsTypePrefer> datas = goodsTypePreferMapper.queryByCourierId(courierId);

        if(CollectionUtils.isEmpty(datas)) return Collections.EMPTY_LIST;
        GoodsTypePrefer data = datas.get(0);

        // 解析数据
        String typePrefers = data.getOrderGoodsPrefer();
        if(StringUtils.isBlank(typePrefers)) return Collections.EMPTY_LIST;

        // 取前三条数据
        String[] typePreferArr = typePrefers.split(",");
        if(typePreferArr.length > 3) {
            typePreferArr = Arrays.copyOf(typePreferArr, 3);
        }

        // 组装成对象
        List<GoodsTypePrefer> result = new ArrayList<>();
        // 获取字典数据
        for(String typePreferStr : typePreferArr) {
            GoodsTypePrefer goodsTypePrefer = new GoodsTypePrefer();
            String[] typePreferStrSplit = typePreferStr.split(":");
            goodsTypePrefer.setGoodsType(typePreferStrSplit[0]);
            goodsTypePrefer.setPrefer(typePreferStrSplit[1]);
            // 获取优先级
            goodsTypePrefer.setCoefficient(groupBysubTypeNameMap.get(goodsTypePrefer.getGoodsType()));
            result.add(goodsTypePrefer);
        }

        // 按照先按照prefer降序再按照coefficient降序排序
        Collections.sort(result);
        //result.stream().forEach(x -> x.setCoefficient(null));
        return result;
    }
}

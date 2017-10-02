package com.ishansong.serviceimpl.goods;

import com.ishansong.dao.goods.GoodsProficiencyMapper;
import com.ishansong.model.goods.GoodsProficiency;
import com.ishansong.service.goods.GoodsProficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangguoliang on 2017/9/26 下午2:42.
 * <p>
 * 闪送员常用物品-配送熟练度
 */
@Service("goodsProficiencyService")
public class GoodsProficiencyServiceImpl implements GoodsProficiencyService {

    @Autowired
    private GoodsProficiencyMapper goodsProficiencyMapper;

    @Override
    public List<GoodsProficiency> queryByCourierId(String courierId) {
        return goodsProficiencyMapper.queryByCourierId(courierId);
    }
}

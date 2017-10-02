package com.ishansong.serviceimpl.biordermatched;

import com.ishansong.dao.biordermatched.BiOrderMatchedMapper;
import com.ishansong.model.biordermatched.BiOrderMatched;
import com.ishansong.service.biordermatched.BiOrderMatchedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/8/10 下午4:49.
 * <p>
 * (描述)
 */
@Service("biOrderMatchedService")
public class BiOrderMatchedServiceImpl implements BiOrderMatchedService {


    @Autowired
    private BiOrderMatchedMapper biOrderMatchedMapper;
    @Override
    public List<BiOrderMatched> queryByCourierId(String courierId) {

        return biOrderMatchedMapper.queryByCourierId(courierId);
    }

    @Override
    public List<BiOrderMatched> queryByOrderNumber(String orderNumber) {

        return biOrderMatchedMapper.queryByOrderNumber(orderNumber);
    }

    @Override
    public List<BiOrderMatched> queryByAll() {
        return biOrderMatchedMapper.queryByAll();
    }
}

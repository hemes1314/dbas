package com.ishansong.serviceimpl.courier;

import com.ishansong.dao.courier.CourierCreditScoreMapper;
import com.ishansong.model.courier.CourierCreditScore;
import com.ishansong.service.courier.CourierCreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/8/10 下午4:48.
 * <p>
 * (描述)
 */
@Service("courierCreditScoreService")
public class CourierCreditScoreServiceImpl implements CourierCreditScoreService {


    @Autowired
    private CourierCreditScoreMapper courierCreditScoreMapper;
    @Override
    public List<CourierCreditScore> queryByCourierId(String courierId) {
        return courierCreditScoreMapper.queryByCourierId(courierId);
    }
}

package com.ishansong.serviceimpl.courier;

import com.ishansong.dao.courier.CourierCreditScoreNewMapper;
import com.ishansong.model.courier.CourierCreditScoreNew;
import com.ishansong.service.courier.CourierCreditScoreNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:24.
 * <p>
 * (描述)
 */
@Service("courierCreditScoreNewService")
public class CourierCreditScoreNewServiceImpl implements CourierCreditScoreNewService{

    @Autowired
    private CourierCreditScoreNewMapper courierCreditScoreNewMapper;

    @Override
    public List<CourierCreditScoreNew> queryByCourierId(String courierId) {
        return courierCreditScoreNewMapper.queryByCourierId(courierId);
    }
}

package com.ishansong.serviceimpl.courier;

import com.ishansong.dao.courier.CourierServiceScoreMapper;
import com.ishansong.model.courier.CourierServiceScore;
import com.ishansong.service.courier.CourierServiceScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:26.
 * <p>
 * (描述)
 */
@Service("courierServiceScoreService")
public class CourierServiceScoreServiceImpl  implements CourierServiceScoreService {

    @Autowired
    private CourierServiceScoreMapper courierServiceScoreMapper;

    @Override
    public List<CourierServiceScore> queryByCourierId(String courierId) {
        return courierServiceScoreMapper.queryByCourierId(courierId);
    }


}

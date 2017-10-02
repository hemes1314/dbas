package com.ishansong.service.courier;

import com.ishansong.model.courier.CourierServiceScore;

import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:25.
 * <p>
 * (描述)
 */
public interface CourierServiceScoreService {

    public List<CourierServiceScore> queryByCourierId(String courierId);

}

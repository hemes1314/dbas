package com.ishansong.service.courier;

import com.ishansong.model.courier.CourierCreditScoreNew;

import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:24.
 * <p>
 * (描述)
 */
public interface CourierCreditScoreNewService {

    public List<CourierCreditScoreNew> queryByCourierId(String courierId);

}

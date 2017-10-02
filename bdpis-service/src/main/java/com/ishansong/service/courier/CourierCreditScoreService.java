package com.ishansong.service.courier;

import com.ishansong.model.courier.CourierCreditScore;

import java.util.List;

/**
 * Created by iss on 2017/8/9 下午12:02.
 * <p>
 * (描述)
 */
public interface CourierCreditScoreService {

    public List<CourierCreditScore> queryByCourierId(String courierId);

}

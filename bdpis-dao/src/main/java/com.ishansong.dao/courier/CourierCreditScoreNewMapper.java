package com.ishansong.dao.courier;


import com.ishansong.model.courier.CourierCreditScoreNew;

import java.util.List;

/**
 * Created by Roger on 17/7/14.
 */
public interface CourierCreditScoreNewMapper {
    public List<CourierCreditScoreNew> queryByCourierId(String courierId);
}

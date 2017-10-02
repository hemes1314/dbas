package com.ishansong.dao.courier;


import com.ishansong.model.courier.CourierCreditScore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Roger on 17/7/14.
 */
@Repository
public interface CourierCreditScoreMapper {
    public List<CourierCreditScore> queryByCourierId(String courierId);
}

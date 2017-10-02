package com.ishansong.dao.courier;


import com.ishansong.model.courier.CourierServiceScore;

import java.util.List;

/**
 * Created by iss on 2017/8/21 下午4:57.
 * <p>
 * 服务分mapper
 */
public interface CourierServiceScoreMapper {

    public List<CourierServiceScore> queryByCourierId(String courierId);

}

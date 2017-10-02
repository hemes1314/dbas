package com.ishansong.dao.courier;


import com.ishansong.model.courier.CourierServiceScore_batch;

import java.util.List;

/**
 * Created by iss on 2017/8/21 下午4:57.
 * <p>
 * 服务分mapper
 */
public interface CourierServiceScoreMapper_batch {

    public List<CourierServiceScore_batch> GetscoreList(List<String> courierId);

}

package com.ishansong.service.courier;

import com.ishansong.model.courier.CourierServiceScore_batch;

import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:27.
 * <p>
 * (描述)
 */
public interface CourierServiceScore_batchService {

    public List<CourierServiceScore_batch> GetscoreList(List<String> courierId);

}

package com.ishansong.serviceimpl.courier;

import com.ishansong.dao.courier.CourierServiceScoreMapper_batch;
import com.ishansong.model.courier.CourierServiceScore_batch;
import com.ishansong.service.courier.CourierServiceScore_batchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:28.
 * <p>
 * (描述)
 */
@Service("courierServiceScore_batchService")
public class CourierServiceScore_batchServiceImpl implements CourierServiceScore_batchService {

    @Autowired
    private CourierServiceScoreMapper_batch courierServiceScoreMapper_batch;


    @Override
    public List<CourierServiceScore_batch> GetscoreList(List<String> courierId) {
        return courierServiceScoreMapper_batch.GetscoreList(courierId);
    }
}

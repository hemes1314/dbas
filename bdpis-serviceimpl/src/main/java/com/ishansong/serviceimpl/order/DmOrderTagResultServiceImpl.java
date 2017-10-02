package com.ishansong.serviceimpl.order;

import com.ishansong.dao.order.DmOrderTagResultMapper;
import com.ishansong.model.order.DmOrderTagResult;
import com.ishansong.service.order.DmOrderTagResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/8/10 下午4:50.
 * <p>
 * (描述)
 */
@Service("dmOrderTagResultService")
public class DmOrderTagResultServiceImpl implements DmOrderTagResultService {

    @Autowired
    private DmOrderTagResultMapper dmOrderTagResultMapper;

    @Override
    public List<DmOrderTagResult> queryByOrderNumber(String orderNumber) {
        return dmOrderTagResultMapper.queryByOrderNumber(orderNumber);
    }
}

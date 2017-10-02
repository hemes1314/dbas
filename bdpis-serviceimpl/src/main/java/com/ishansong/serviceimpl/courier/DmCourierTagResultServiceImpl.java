package com.ishansong.serviceimpl.courier;

import com.ishansong.dao.courier.DmCourierTagResultMapper;
import com.ishansong.model.courier.DmCourierTagResult;
import com.ishansong.service.courier.DmCourierTagResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/8/10 下午4:48.
 * <p>
 * (描述)
 */
@Service("dmCourierTagResultService")
public class DmCourierTagResultServiceImpl implements DmCourierTagResultService {

    @Autowired
    private DmCourierTagResultMapper dmCourierTagResultMapper;

    @Override
    public List<DmCourierTagResult> queryByCourierId(String courierId) {

        return dmCourierTagResultMapper.queryByCourierId(courierId);
    }
}

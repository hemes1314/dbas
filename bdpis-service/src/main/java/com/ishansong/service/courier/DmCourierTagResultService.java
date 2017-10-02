package com.ishansong.service.courier;

import com.ishansong.model.courier.DmCourierTagResult;

import java.util.List;

/**
 * Created by iss on 2017/8/9 下午12:03.
 * <p>
 * (描述)
 */
public interface DmCourierTagResultService {

    public List<DmCourierTagResult> queryByCourierId(String courierId);

}

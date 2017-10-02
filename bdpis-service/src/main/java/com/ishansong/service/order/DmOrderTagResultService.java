package com.ishansong.service.order;

import com.ishansong.model.order.DmOrderTagResult;

import java.util.List;

/**
 * Created by iss on 2017/8/9 下午12:04.
 * <p>
 * (描述)
 */
public interface DmOrderTagResultService {

    public List<DmOrderTagResult> queryByOrderNumber(String orderNumber);

}

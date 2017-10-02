package com.ishansong.dao.order;


import com.ishansong.model.order.DmOrderTagResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iss on 2017/6/23 上午11:03.
 * <p>
 * (描述)
 */
@Repository
public interface DmOrderTagResultMapper {

    public List<DmOrderTagResult> queryByOrderNumber(String orderNumber);


}

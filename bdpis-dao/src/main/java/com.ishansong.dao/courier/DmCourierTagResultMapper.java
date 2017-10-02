package com.ishansong.dao.courier;


import com.ishansong.model.courier.DmCourierTagResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iss on 2017/6/23 上午10:59.
 * <p>
 * (描述)
 */
@Repository
public interface DmCourierTagResultMapper {


    public List<DmCourierTagResult> queryByCourierId(String courierId);

}

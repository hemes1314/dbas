package com.ishansong.service.biordermatched;

import com.ishansong.model.biordermatched.BiOrderMatched;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangguoliang on 2017/8/9 下午12:00.
 * <p>
 * (描述)
 */
public interface BiOrderMatchedService {


    public List<BiOrderMatched> queryByCourierId(String courierId);

    public List<BiOrderMatched> queryByOrderNumber(String orderNumber);

    public List<BiOrderMatched> queryByAll();



}

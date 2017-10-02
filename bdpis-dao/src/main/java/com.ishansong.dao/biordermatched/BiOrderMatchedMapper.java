package com.ishansong.dao.biordermatched;


import com.ishansong.model.biordermatched.BiOrderMatched;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangguoliang on 2017/6/19.
 *
 *  对应的mapper文件
 */
@Repository
public interface BiOrderMatchedMapper {

    public List<BiOrderMatched> queryByCourierId(String courierId);

    public List<BiOrderMatched> queryByOrderNumber(String orderNumber);
    public List<BiOrderMatched> getByOrderNumber(String orderNumber);

    public List<BiOrderMatched> queryByAll();

}

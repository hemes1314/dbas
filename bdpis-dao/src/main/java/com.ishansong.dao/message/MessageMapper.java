package com.ishansong.dao.message;


import com.ishansong.model.message.Message;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by yangguoliang on 2017/6/19.
 *
 *  对应的mapper文件
 */
@Repository
public interface MessageMapper{

    public List<Message> queryByAll();
}
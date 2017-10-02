package com.ishansong.serviceimpl.message;

import com.ishansong.dao.message.MessageMapper;
import com.ishansong.model.message.Message;
import com.ishansong.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/8/10 下午4:50.
 * <p>
 * (描述)
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {


    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> queryByAll() {
        return messageMapper.queryByAll();
    }
}

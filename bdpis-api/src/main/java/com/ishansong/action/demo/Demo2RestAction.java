package com.ishansong.action.demo;

import com.ishansong.action.utils.DictionariesLoad;
import com.ishansong.common.util.ObjectUtils;
import com.ishansong.model.biordermatched.BiOrderMatched;
import com.ishansong.service.biordermatched.BiOrderMatchedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by yangguoliang on 2017/6/14.
 * <p>
 * 环境测试demo
 */

@Controller
@RequestMapping("/demo")
public class Demo2RestAction {
    private static final Logger logger = LogManager.getLogger(Demo2RestAction.class.getName());

    @Autowired
    @Resource
    private BiOrderMatchedService biOrderMatchedService;

    @RequestMapping(value="/index/{id}",method = RequestMethod.GET)
    public @ResponseBody
    String hello(@PathVariable(value = "id") String  id){
        logger.info("aaaaa ddddddddddd===============");

        return id+"hello";
    }

    @RequestMapping(value="/getPortrait/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getPortraitByCourierId(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {

        List<BiOrderMatched> biOrderMatchedList = biOrderMatchedService.queryByCourierId(courierId);
        if(biOrderMatchedList!= null && biOrderMatchedList.size() > 0){
            for (BiOrderMatched biOrderMatched:biOrderMatchedList) {
                logger.info(biOrderMatched.getCourier_id());
            }
        }
        String json = ObjectUtils.toJson(biOrderMatchedList);
        System.out.println(json.toString());
        String resultstr = json.toString();
        return resultstr;
    }
}

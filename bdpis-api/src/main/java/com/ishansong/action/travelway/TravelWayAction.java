package com.ishansong.action.travelway;

import com.ishansong.model.other.ResultInfo;
import net.sf.json.JSONObject;
import com.ishansong.common.util.AESUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

/**
 * Created by yangguoliang on 2017/6/16 下午5:30.
 * <p>
 * 画像服务--交通工具接口
 *
 */
@Controller
@RequestMapping("/travelWay")
public class TravelWayAction {

    private static final Logger logger = LogManager.getLogger(TravelWayAction.class.getName());


    @RequestMapping(value="/remark/{i}",method = RequestMethod.GET)
    public  @ResponseBody
    String remark(@PathVariable(value = "i") String  i) {
        String result = i;
        return "<Result>" + result + "</Result>" ;
    }

    /**
     * 加密操作
     * 提供给开发联调
     * */
    @RequestMapping(value="/getremark1/{remarkContent}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody String getremark1(@PathVariable(value = "remarkContent") String  remarkContent) throws Exception {
        logger.info("时间："+new Date()+",开发访问接口getremark1 ，请求内容为 ："+remarkContent);

        ResultInfo resultInfo = new ResultInfo();
        Object result = null;
        String  code = "0";
        String message = "";
        try {
            //对传递过来的数据先进行解密
            String remarkContentMessage = AESUtils.Decrypt(remarkContent);
            TrafficParticiple6 trafficParticiple = new TrafficParticiple6();
            String restStr = trafficParticiple.traffic_participle_minging(remarkContentMessage);
            //进行加密
            if(!org.apache.commons.lang.StringUtils.isEmpty(restStr)){
                result = AESUtils.Encrypt(restStr,AESUtils.cKey);
                message = "成功";
            }else{
                message = "无结果";
                code = "2";
            }
        }catch (Exception e){
            logger.info("异常信息："+e.getMessage());
            logger.info("异常内容：");
            e.printStackTrace();
            message ="失败";
            code = "1";
        }
        resultInfo.setResult(result);
        resultInfo.setMessage(message);
        resultInfo.setCode(code);

        JSONObject json = JSONObject.fromObject(resultInfo);
        logger.info(json.toString());
        String resultstr = json.toString();
        logger.info("时间："+new Date()+",开发访问接口getremark1，返回内容为 ："+resultstr);
        return resultstr;
    }

    /***
     *
     *
     *
     * 不加密操作
     * 提供给测试
     */
    @RequestMapping(value="/getremark2/{remarkContent}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody  String getremark2(@PathVariable(value = "remarkContent") String  remarkContent) throws Exception {
        remarkContent=new String(remarkContent.getBytes("ISO-8859-1"), "utf8");
        logger.info("时间："+new Date()+",测试访问接口getremark2 ，请求内容为 ："+remarkContent);
        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        Object result = null;
        String message = "";
        try {
            //对传递过来的数据先进行解密
            String remarkContentMessage = remarkContent;
            TrafficParticiple6 trafficParticiple = new TrafficParticiple6();
            String restStr = trafficParticiple.traffic_participle_minging(remarkContentMessage);
            //进行加密
            if(!org.apache.commons.lang.StringUtils.isEmpty(restStr)){
                result = restStr;
                message = "成功";
            }else{
                message = "无结果";
                code = "2";
            }
        }catch (Exception e){
            logger.info("异常信息："+e.getMessage());
            logger.info("异常内容：");
            e.printStackTrace();
            message ="失败";
            code = "1";
        }
        resultInfo.setResult(result);
        resultInfo.setMessage(message);
        resultInfo.setCode(code);

        JSONObject json = JSONObject.fromObject(resultInfo);
        logger.info(json.toString());
        String resultstr = json.toString();
        logger.info("时间："+new Date()+",测试访问接口getremark2，返回内容为 ："+resultstr);
        return resultstr;
    }


}

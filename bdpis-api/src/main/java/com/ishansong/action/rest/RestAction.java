package com.ishansong.action.rest;

import com.ishansong.action.travelway.TrafficParticiple6;
import com.ishansong.common.util.AESUtils;
import com.ishansong.common.util.ObjectUtils;
import com.ishansong.model.courier.CourierCreditScore;
import com.ishansong.model.courier.CourierCreditScoreNew;
import com.ishansong.model.courier.CourierServiceScore;
import com.ishansong.model.courier.DmCourierTagResult;
import com.ishansong.model.order.DmOrderTagResult;
import com.ishansong.model.other.ResultOldInfo;
import com.ishansong.service.courier.CourierCreditScoreNewService;
import com.ishansong.service.courier.CourierCreditScoreService;
import com.ishansong.service.courier.CourierServiceScoreService;
import com.ishansong.service.courier.DmCourierTagResultService;
import com.ishansong.service.order.DmOrderTagResultService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by iss on 2017/9/15 上午11:06.
 * <p>
 * 还原之前的历史接口
 */
@Controller
@RequestMapping("/rest")
public class RestAction {

    private static final Logger logger = LogManager.getLogger(RestAction.class.getName());


    @Autowired
    private CourierCreditScoreNewService courierCreditScoreNewService;
    @Autowired
    private CourierServiceScoreService courierServiceScoreService;
    @Autowired
    private DmCourierTagResultService dmCourierTagResultService;
    @Autowired
    private CourierCreditScoreService courierCreditScoreService;
    @Autowired
    private DmOrderTagResultService dmOrderTagResultService;

    /**
     * 交通工具接口
     * 加密操作
     * 提供给开发联调
     * */
    @RequestMapping(value="/TravelWayService/getremark1/{str}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getremark1(@PathVariable(value = "str") String  str) throws Exception {
        logger.info("时间："+new Date()+",开发访问接口getremark1 ，请求内容为 ："+str);

        ResultOldInfo resultInfo = new ResultOldInfo();
        String result = "0";
        String  resultstrAES = "";
        try {
            //对传递过来的数据先进行解密
            String message = AESUtils.Decrypt(str);
            TrafficParticiple6 trafficParticiple = new TrafficParticiple6();
            String restStr = trafficParticiple.traffic_participle_minging(message);
            //进行加密
            if(!org.apache.commons.lang.StringUtils.isEmpty(restStr)){
                resultstrAES = AESUtils.Encrypt(restStr,AESUtils.cKey);
            }else{
                resultstrAES = AESUtils.Encrypt("无结果",AESUtils.cKey);
                result = "2";
            }
        }catch (Exception e){
            logger.info("异常信息："+e.getMessage());
            logger.info("异常内容：");
            e.printStackTrace();
            resultstrAES =AESUtils.Encrypt("系统异常",AESUtils.cKey);
            result = "1";
        }
        resultInfo.setResult(resultstrAES);
        resultInfo.setMessage(result);

        JSONObject json = JSONObject.fromObject(resultInfo);
        logger.info(json.toString());
        String resultstr = json.toString();
        logger.info("时间："+new Date()+",开发访问接口getremark1，返回内容为 ："+resultstr);
        return resultstr;
    }

    /***
     *
     *
     * 交通工具开发接口
     * 不加密操作
     * 提供给测试
     */
    @RequestMapping(value="/TravelWayService/getremark2/{str}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getremark2(@PathVariable(value = "str") String  str) throws Exception {
        str=new String(str.getBytes("ISO-8859-1"), "utf8");
        logger.info("时间："+new Date()+",测试访问接口getremark2 ，请求内容为 ："+str);
        ResultOldInfo resultInfo = new ResultOldInfo();
        String result = "0";
        String  resultstrAES = "";
        try {
            //对传递过来的数据先进行解密
            String message = str;
            TrafficParticiple6 trafficParticiple = new TrafficParticiple6();
            String restStr = trafficParticiple.traffic_participle_minging(message);
            if(!org.apache.commons.lang.StringUtils.isEmpty(restStr)){
                resultstrAES = restStr;
            }else{
                resultstrAES = "无结果";
                result = "2";
            }
        }catch (Exception e){
            logger.info("异常信息："+e.getMessage());
            logger.info("异常内容：");
            e.printStackTrace();
            resultstrAES ="系统异常";
            result = "1";
        }
        resultInfo.setResult(resultstrAES);
        resultInfo.setMessage(result);

        JSONObject json = JSONObject.fromObject(resultInfo);
        logger.info(json.toString());
        String resultstr = json.toString();
        logger.info("时间："+new Date()+",测试访问接口getremark2，返回内容为 ："+resultstr);
        return resultstr;
    }


    /**
     *信用分服务接口
     */
    @RequestMapping(value="/creditscore/getCourierCreditScore/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getCourierCreditScoreNewByCourierId(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {
        List<CourierCreditScoreNew> courierCreditScoreNewList = courierCreditScoreNewService.queryByCourierId(courierId);
        if(courierCreditScoreNewList!= null && courierCreditScoreNewList.size() > 0){
            for (CourierCreditScoreNew courierCreditScore:courierCreditScoreNewList) {
                logger.info(courierCreditScore.getCity_id());
            }
        }
        String json = ObjectUtils.toJson(courierCreditScoreNewList);
        logger.info(json.toString());
        String resultstr = json.toString();
        return resultstr;
    }




    @RequestMapping(value="/portrait/getDmOrderResult/orderNumber/{orderNumber}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getDmOrderResultByOrderNumber(@PathVariable(value = "orderNumber") String  orderNumber) throws UnsupportedEncodingException, IOException {
        List<DmOrderTagResult> dmOrderTagResults = dmOrderTagResultService.queryByOrderNumber(orderNumber);
        if(dmOrderTagResults!= null && dmOrderTagResults.size() > 0){
            for (DmOrderTagResult dmOrderTagResult:dmOrderTagResults) {
                logger.info(dmOrderTagResult.getGoods());
            }
        }
        String json = ObjectUtils.toJson(dmOrderTagResults);
        logger.info(json.toString());
        String resultstr = json.toString();
        return resultstr;
    }


    @RequestMapping(value="/portrait/getDmCourierResult/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getDmCourierResultByOrderNumber(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {
        List<DmCourierTagResult> dmCourierTagResultList = dmCourierTagResultService.queryByCourierId(courierId);
        if(dmCourierTagResultList!= null && dmCourierTagResultList.size() > 0){
            for (DmCourierTagResult dmCourierTagResult:dmCourierTagResultList) {
                logger.info(dmCourierTagResult.getAvg_speed());
            }
        }
        String json = ObjectUtils.toJson(dmCourierTagResultList);
        logger.info(json.toString());
        String resultstr = json.toString();
        return resultstr;
    }



    @RequestMapping(value="/portrait/getCourierCreditScore/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getCourierCreditScoreByCourierId(@PathVariable(value = "courierId") String  courierId ) throws UnsupportedEncodingException, IOException {

        List<CourierCreditScore> courierCreditScoreList = courierCreditScoreService.queryByCourierId(courierId);
        if(courierCreditScoreList!= null && courierCreditScoreList.size() > 0){
            for (CourierCreditScore courierCreditScore:courierCreditScoreList) {
                logger.info(courierCreditScore.getScore());
            }
        }
        String json = ObjectUtils.toJson(courierCreditScoreList);
        logger.info(json.toString());
        String resultstr = json.toString();
        return resultstr;
    }



    /**
     *
     *
     *服务分
     * */
    @RequestMapping(value="/servicescore/getCourierServiceScore/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getCourierServiceScoreByCourierId(@PathVariable(value = "courierId") String  courierId ) throws UnsupportedEncodingException, IOException {


        List<CourierServiceScore> courierServiceScoreList = courierServiceScoreService.queryByCourierId(courierId);
        if(courierServiceScoreList!= null && courierServiceScoreList.size() > 0){
            for (CourierServiceScore courierCreditScore:courierServiceScoreList) {
                logger.info(courierCreditScore.getCity_id());
            }
        }
        String json = ObjectUtils.toJson(courierServiceScoreList);
        logger.info(json.toString());
        String resultstr = json.toString();
        return resultstr;
    }


}

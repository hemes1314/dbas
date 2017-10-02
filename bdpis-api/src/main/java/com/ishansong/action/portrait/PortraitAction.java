package com.ishansong.action.portrait;

import com.ishansong.model.other.ResultInfo;
import com.ishansong.model.biordermatched.BiOrderMatched;
import com.ishansong.model.courier.CourierCreditScore;
import com.ishansong.model.courier.DmCourierTagResult;
import com.ishansong.model.order.DmOrderTagResult;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import com.ishansong.service.courier.CourierCreditScoreService;
import com.ishansong.service.courier.DmCourierTagResultService;
import com.ishansong.service.order.DmOrderTagResultService;
import com.ishansong.service.biordermatched.BiOrderMatchedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import net.sf.json.JSONObject;



/**
 * Created by yangguoliang on 2017/6/19.
 * <p>
 * 将画像结果数据进行返回
 */
@Controller
@RequestMapping("/portrait")
public class PortraitAction {

    private static final Logger logger = LogManager.getLogger(PortraitAction.class.getName());

    @Autowired
    private BiOrderMatchedService biOrderMatchedService;
    @Autowired
    private DmCourierTagResultService dmCourierTagResultService;
    @Autowired
    private CourierCreditScoreService courierCreditScoreService;
    @Autowired
    private DmOrderTagResultService dmOrderTagResultService;


    @RequestMapping(value="/getPortrait/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody
    String getPortraitByCourierId(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<BiOrderMatched> biOrderMatchedList = biOrderMatchedService.queryByCourierId(courierId);
            if(biOrderMatchedList!= null && biOrderMatchedList.size() > 0){
                for (BiOrderMatched biOrderMatched:biOrderMatchedList) {
                    logger.info(biOrderMatched.getCourier_id());
                }
                result = biOrderMatchedList;
                message = "成功";
            }else{
                code= "2";
                message = "无结果";
            }
        }catch (Exception e){
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
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
        return resultstr;
    }

    @RequestMapping(value="/getPortrait/orderNumber/{orderNumber}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody
    String getPortraitByOrderNumber(@PathVariable(value = "orderNumber") String  orderNumber) throws UnsupportedEncodingException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<BiOrderMatched> biOrderMatchedList = biOrderMatchedService.queryByOrderNumber(orderNumber);
            if(biOrderMatchedList!= null && biOrderMatchedList.size() > 0){
                for (BiOrderMatched biOrderMatched:biOrderMatchedList) {
                    logger.info(biOrderMatched.getCourier_id());
                }
                result = biOrderMatchedList;
                message = "成功";
            }else{
                code= "2";
                message = "无结果";
            }
        }catch (Exception e){
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
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
        return resultstr;
    }





    @RequestMapping(value="/getDmOrderResult/orderNumber/{orderNumber}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody String getDmOrderResultByOrderNumber(@PathVariable(value = "orderNumber") String  orderNumber) throws UnsupportedEncodingException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<DmOrderTagResult> dmOrderTagResults = dmOrderTagResultService.queryByOrderNumber(orderNumber);
            if(dmOrderTagResults!= null && dmOrderTagResults.size() > 0){
                for (DmOrderTagResult dmOrderTagResult:dmOrderTagResults) {
                    logger.info(dmOrderTagResult.getGoods());
                }
                result = dmOrderTagResults;
                message = "成功";
            }else{
                code= "2";
                message = "无结果";
            }

        }catch (Exception e){
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
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
        return resultstr;
    }





    @RequestMapping(value="/getDmCourierResult/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody String getDmCourierResultByOrderNumber(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {


        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<DmCourierTagResult> dmCourierTagResultList = dmCourierTagResultService.queryByCourierId(courierId);
            if(dmCourierTagResultList!= null && dmCourierTagResultList.size() > 0){
                for (DmCourierTagResult dmCourierTagResult:dmCourierTagResultList) {
                    logger.info(dmCourierTagResult.getAvg_speed());
                }
               result = dmCourierTagResultList;
               message = "成功";
            }else{
                code= "2";
                message = "无结果";
            }
        }catch (Exception e){
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
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
        return resultstr;
    }



    /**
     *
     * 画像服务---信誉分接口
     *
     * */
    @RequestMapping(value="/getCourierCreditScore/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody String getCourierCreditScoreByCourierId(@PathVariable(value = "courierId") String  courierId ) throws  IOException {
        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<CourierCreditScore> courierCreditScoreList = courierCreditScoreService.queryByCourierId(courierId);
            if(courierCreditScoreList!= null && courierCreditScoreList.size() > 0){
                for (CourierCreditScore courierCreditScore:courierCreditScoreList) {
                    logger.info(courierCreditScore.getScore());
                }
                result = courierCreditScoreList;
                message = "成功";
            }else{
                code= "2";
                message = "无结果";
            }
        }catch (Exception e){
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
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
        return resultstr;
    }
}

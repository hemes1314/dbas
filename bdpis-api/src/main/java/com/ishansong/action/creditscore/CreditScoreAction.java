package com.ishansong.action.creditscore;

import com.ishansong.model.other.ResultInfo;
import com.ishansong.model.courier.CourierCreditScoreNew;
import com.ishansong.service.courier.CourierCreditScoreNewService;
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
import java.util.List;

/**
 * Created by iss on 2017/8/21 下午4:55.
 * <p>
 * 画像服务--信用分接口
 */
@Controller
@RequestMapping("/creditscore")
public class CreditScoreAction {

    private static final Logger logger = LogManager.getLogger(CreditScoreAction.class.getName());

    @Autowired
    private CourierCreditScoreNewService courierCreditScoreNewService;

    @RequestMapping(value="/getCourierCreditScore/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody
    String getCourierCreditScoreByCourierId(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<CourierCreditScoreNew>  courierCreditScoreNewList  =   courierCreditScoreNewService.queryByCourierId(courierId);
            if(courierCreditScoreNewList!= null && courierCreditScoreNewList.size() > 0){
                for (CourierCreditScoreNew courierCreditScoreNew:courierCreditScoreNewList) {
                    logger.info(courierCreditScoreNew.getCourier_id());
                }
                result = courierCreditScoreNewList;
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

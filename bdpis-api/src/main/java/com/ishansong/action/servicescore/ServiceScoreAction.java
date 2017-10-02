package com.ishansong.action.servicescore;

import com.ishansong.model.other.ResultInfo;
import com.ishansong.model.courier.CourierServiceScore;
import com.ishansong.model.courier.CourierServiceScore_batch;
import com.ishansong.model.courier.CourierServiceScorebatch;
import com.ishansong.service.courier.CourierServiceScoreService;
import com.ishansong.service.courier.CourierServiceScore_batchService;
import net.sf.json.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by iss on 2017/9/12 下午7:41.
 * <p>
 * 画像服务--服务分接口
 */
@Controller
@RequestMapping("/serviceScore")
public class ServiceScoreAction {


    private static final Logger logger = LogManager.getLogger(ServiceScoreAction.class.getName());

    @Autowired
    private CourierServiceScore_batchService courierServiceScoreBatchService;
    @Autowired
    private CourierServiceScoreService courierServiceScoreService;

    @RequestMapping(value="/getCourierServiceScore/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody
    String getCourierServiceScoreByCourierId(@PathVariable(value = "courierId") String  courierId) throws UnsupportedEncodingException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<CourierServiceScore> courierServiceScoreList  =   courierServiceScoreService.queryByCourierId(courierId);
            if(courierServiceScoreList!= null && courierServiceScoreList.size() > 0){
                for (CourierServiceScore courierServiceScore:courierServiceScoreList) {
                    logger.info(courierServiceScore.getCourier_id());
                }
                result = courierServiceScoreList;
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



    @RequestMapping(value="/getCourierServiceScore_batch/courierIds/{courierIds}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody
    String getCourierServiceScore_batchByCourierIds(@PathVariable(value = "courierIds") String  courierIds) throws UnsupportedEncodingException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            String[] sourceStrArray=courierIds.replace("'","").split(",");
            List<String> wordList = Arrays.asList(sourceStrArray);
            List<CourierServiceScore_batch> courierServiceScoreBatchList  =   courierServiceScoreBatchService.GetscoreList(wordList);
            if(courierServiceScoreBatchList!= null && courierServiceScoreBatchList.size() > 0){
                //将查询出来的结果，名称做转换
                List<CourierServiceScorebatch> courierServiceScorebatches = new ArrayList<CourierServiceScorebatch>();
                for (CourierServiceScore_batch courierServiceScore_batch:courierServiceScoreBatchList) {
                    logger.info(courierServiceScore_batch.getCourier_id());
                    CourierServiceScorebatch courierServiceScorebatch = new CourierServiceScorebatch();
                    courierServiceScorebatch.setCourierId(courierServiceScore_batch.getCourier_id());
                    courierServiceScorebatch.setServiceScore(courierServiceScore_batch.getService_score());
                    courierServiceScorebatches.add(courierServiceScorebatch);
                }
                result = courierServiceScorebatches;
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

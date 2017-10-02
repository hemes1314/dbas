package com.ishansong.action.goods;
import com.alibaba.fastjson.JSON;
import com.ishansong.action.utils.DictionariesLoad;
import com.ishansong.model.goods.GoodsTypePrefer;
import com.ishansong.model.other.ResultInfo;
import com.ishansong.service.goods.GoodsTypeService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wubin on 2017/9/26
 * <p>
 * 闪送员常用物品--商品分类接口
 */
@Controller
@RequestMapping("/goodstype")
public class GoodsTypeAction {

    private static final Logger logger = LogManager.getLogger(GoodsTypeAction.class);

    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping(value="/prefer/courierId/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public @ResponseBody
    Object getGoodsTypePreferByCourierId(@PathVariable(value = "courierId") String  courierId) {

        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            List<GoodsTypePrefer> goodsTypePreferList = goodsTypeService.queryPreferByCourierId(courierId, DictionariesLoad.groupBysubTypeNameMap);
            if(CollectionUtils.isNotEmpty(goodsTypePreferList)){
                for (GoodsTypePrefer v : goodsTypePreferList) {
                    logger.info(v.getCourierId());
                }
                result = goodsTypePreferList;
                message = "成功";
            }else{
                code= "2";
                message = "无结果";
            }
        }catch (Exception e){
            logger.error("异常信息：" + e.getMessage());
            logger.error("异常内容：");
            e.printStackTrace();
            message ="失败";
            code = "1";
        }

        resultInfo.setResult(result);
        resultInfo.setMessage(message);
        resultInfo.setCode(code);

//        JSONObject json = JSONObject.fromObject(resultInfo);
//        logger.info(json.toString());
//        String resultstr = json.toString();

        return JSON.toJSONString(resultInfo);
    }

}

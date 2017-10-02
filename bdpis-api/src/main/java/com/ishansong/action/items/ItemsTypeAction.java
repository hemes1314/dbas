package com.ishansong.action.items;

import com.ishansong.model.other.ResultInfo;
import com.ishansong.action.utils.DictionariesLoad;
import com.ishansong.common.util.WordSegmenterUtils;
import com.ishansong.model.dictionaries.CdCodeWordseg;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by iss on 2017/9/22 下午3:00.
 * <p>
 * 实时物品画像标签
 */
@Controller
@RequestMapping("/itemsType")
public class ItemsTypeAction {

    private static final Logger logger = LogManager.getLogger(ItemsTypeAction.class.getName());

    @RequestMapping(value="/getItemsType/{goodsValue}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody String getItemsType(@PathVariable(value = "goodsValue") String  goodsValue) throws Exception {
        logger.info("时间："+new Date()+",开发访问接口getItemsType ，请求内容为 ："+goodsValue);
        ResultInfo resultInfo = new ResultInfo();
        Object result = null;
        String  code = "0";
        String message = "";
        try{
            if(!StringUtils.isEmpty(goodsValue)){
                //获取缓存中的字典数据
                Map<String,CdCodeWordseg> cdCodeWordsegMap =   DictionariesLoad.dictionaryInfoMap.get("cdCodeWordsegMap");

                //word分词
                Map<String,String> mapStr = WordSegmenterUtils.segMore(goodsValue);

                //使用word 分词器进行分词操作
                int i =0;
                String wordStr = "";
                if(mapStr!=null && mapStr.size() > 0){
                    for (Map.Entry<String, String> entry : mapStr.entrySet()) {
                        if(i == 1){
                            break;
                        }
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        wordStr = entry.getValue();
                        i++;
                    }
                }
                //输出分词结果
                String[] wordArray = wordStr.split(" ");
                logger.info(Arrays.toString(wordArray));
                //将分支之后的物品分类进行规整
                Map<Integer,String> resultMap = new HashMap<Integer,String>();
                //根据物品备注分词之后的结果，将对应的字典信息筛选
                for (int j =0;j<wordArray.length;j++){
                    String context = wordArray[j];
                    if(context.length() > 1){
                        if(cdCodeWordsegMap.containsKey(context)){
                            CdCodeWordseg res =  cdCodeWordsegMap.get(context);
                            resultMap.put(Integer.valueOf(res.getCoefficient()),res.getWord_subtype_name());
                        }
                    }
                }
                //若resultMap.size 的结果为0 的话，将订单物品分类表示为其他。
                System.out.println(resultMap.size());
                if(resultMap!=null && resultMap.size()>0){
                    Set<Integer> set = resultMap.keySet();
                    Object[] obj = set.toArray();
                    Arrays.sort(obj);
                    result = resultMap.get(obj[obj.length-1]);
                }else{
                    result = "其他";
                }
                message = "成功";
            }else{
                message = "参数为空";
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
        logger.info("时间："+new Date()+",开发访问接口getItemsType，返回内容为 ："+resultstr);
        return resultstr;
    }
}

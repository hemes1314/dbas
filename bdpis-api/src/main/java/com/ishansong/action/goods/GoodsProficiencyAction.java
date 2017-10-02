package com.ishansong.action.goods;

import com.ishansong.action.items.ItemsTypeAction;
import com.ishansong.model.other.ResultInfo;
import com.ishansong.action.utils.DictionariesLoad;
import com.ishansong.model.goods.GoodsProficiency;
import com.ishansong.model.other.CollectGoodsProficiencyListFault;
import com.ishansong.service.goods.GoodsProficiencyService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by yangguoliang on 2017/9/26 下午2:35.
 * <p>
 * 闪送员常用物品-配送熟练度
 */
@Controller
@RequestMapping("/goodsProficiency")
public class GoodsProficiencyAction {

    private static final Logger logger = LogManager.getLogger(ItemsTypeAction.class.getName());

    @Autowired
    private GoodsProficiencyService goodsProficiencyService;
    /**
     *
     * 根据闪送员ID 查询闪送员常用物品配送熟练度
     *
     **/
    @RequestMapping(value="/getGoodsProficiency/{courierId}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody
    String getGoodsProficiency(@PathVariable(value = "courierId") String  courierId) throws Exception {
        logger.info("时间："+new Date()+",开发访问接口getGoodsProficiency ，请求内容为 ："+courierId);
        ResultInfo resultInfo = new ResultInfo();
        Object result = null;
        String  code = "0";
        String message = "";
        try{
            if(!StringUtils.isEmpty(courierId)){
                //获取缓存中的字典数据
                Map<String,String> subTypeNameMap =   DictionariesLoad.groupBysubTypeNameMap;
                //获取熟练度数据
                List<GoodsProficiency> goodsProficiencies = goodsProficiencyService.queryByCourierId(courierId);

                if(goodsProficiencies != null && goodsProficiencies.size() > 0){
                    GoodsProficiency goodsProficiency =   goodsProficiencies.get(0);
                    String goodsTags =  goodsProficiency.getGoods_tags();
                    String [] goodsStr = goodsTags.split(",");
                    //利用二维数组进行内部的熟练度和优先级排序
                    String[][] array = new String[goodsStr.length][3];
                    for(int i = 0;i< goodsStr.length;i++){
                        String str = goodsStr[i];
                        String[] msArr = str.split(":");
                        //物品类型
                        array[i][0]=msArr[0];
                        //熟练度
                        array[i][1]=msArr[1];
                        //优先级
                        array[i][2]=subTypeNameMap.get(msArr[0]);
                    }
                    sort(array, new int[] {1,2});   //先根据第二列比较，若相同则再比较第三列排序（都是降序）
                    List<CollectGoodsProficiencyListFault> collectGoodsProficiencyListFaults = new ArrayList<CollectGoodsProficiencyListFault>();
                    for (int i = 0; i < array.length; i++) {
                        CollectGoodsProficiencyListFault collectGoodsProficiencyListFault = new CollectGoodsProficiencyListFault();
                        collectGoodsProficiencyListFault.setGoodsSubtype(array[i][0]);
                        collectGoodsProficiencyListFault.setProficiency(array[i][1]);
                        collectGoodsProficiencyListFaults.add(collectGoodsProficiencyListFault);
                        for (int j = 0; j < array[i].length; j++) {
                            System.out.print(array[i][j]);
                            System.out.print("\t");
                        }
                        System.out.println();
                    }
                    result = collectGoodsProficiencyListFaults;
                    message = "成功";
                }else{
                    message = "无结果";
                    code = "3";
                }
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



    public static void sort(String[][] ob, final int[] order) {
        Arrays.sort(ob, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                String[] one = (String[]) o1;
                String[] two = (String[]) o2;
                for (int i = 0; i < order.length; i++) {
                    int k = order[i];
                    if (Integer.valueOf(one[k]) > Integer.valueOf(two[k])) {
                        return -1;
                    } else if (Integer.valueOf(one[k]) < Integer.valueOf(two[k])) {
                        return 1;
                    } else {
                        continue;  //如果按一条件比较结果相等，就使用第二个条件进行比较。
                    }
                }
                return 0;
            }
        });
    }

}

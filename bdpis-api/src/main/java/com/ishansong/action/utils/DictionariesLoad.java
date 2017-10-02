package com.ishansong.action.utils;

import com.ishansong.model.biordermatched.BiOrderMatched;
import com.ishansong.model.dictionaries.CdCodeWordseg;
import com.ishansong.service.biordermatched.BiOrderMatchedService;
import com.ishansong.service.dictionaries.CdCodeWordsegService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguoliang on 2017/9/20 下午3:18.
 * <p>
 * 读取字典表中的数据加载到dictionaryInfoMap变量当中
 */
public class DictionariesLoad {


    public static Map<String,Map<String,CdCodeWordseg>> dictionaryInfoMap = new HashMap<String, Map<String,CdCodeWordseg>>(); //保存数据字典信息

    //得到group by 之后的物品分类 优先级数据
    public static Map<String,String> groupBysubTypeNameMap = new HashMap<String,String>();

    private CdCodeWordsegService cdCodeWordsegService;


    public void loadData(){
        //查询物品分类数据字典信息
        List<CdCodeWordseg> cdCodeWordsegList = cdCodeWordsegService.queryByAll();

        Map<String,CdCodeWordseg> cdCodeWordsegMap = new HashMap<String,CdCodeWordseg>();
        for(CdCodeWordseg cdCodeWordseg : cdCodeWordsegList){
            cdCodeWordsegMap.put(cdCodeWordseg.getWord_seg(),cdCodeWordseg);
            groupBysubTypeNameMap.put(cdCodeWordseg.getWord_subtype_name(),cdCodeWordseg.getCoefficient());
        }
        dictionaryInfoMap.put("cdCodeWordsegMap",cdCodeWordsegMap);


    }

    public CdCodeWordsegService getCdCodeWordsegService() {
        return cdCodeWordsegService;
    }

    public void setCdCodeWordsegService(CdCodeWordsegService cdCodeWordsegService) {
        this.cdCodeWordsegService = cdCodeWordsegService;
    }
}

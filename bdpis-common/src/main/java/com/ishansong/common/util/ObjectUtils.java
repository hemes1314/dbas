package com.ishansong.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by yangguoliang on 2017/6/22.
 *
 * 对象转换工具类
 */
public class ObjectUtils {


    /**
     * 对象转json
     *
     * @param object 对象
     * @return json
     */
    public static String toJson(Object object) {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 返回结果json数据转map
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> parseJsonToMap(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            //最外层解析
            JSONObject json = JSONObject.fromObject(jsonStr);
            for(Object k : json.keySet()){
                Object v = json.get(k);
                //如果内层还是数组的话，继续解析
                if(v instanceof JSONArray){
                    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                    Iterator<JSONObject> it = ((JSONArray)v).iterator();
                    while(it.hasNext()){
                        JSONObject json2 = it.next();
                        list.add(parseJsonToMap(json2.toString()));
                    }
                    map.put(k.toString(), list);
                } else {
                    map.put(k.toString(), v);
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return map;
    }


    /**
     * json数据转List<Map<String,Object>>
     *
     * @param jsonStr
     * @return
     */
    public static List<Map<String, Object>> parseJsonToList(JSONArray jsonStr){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

        try{
            JSONArray jsonArr = JSONArray.fromObject(jsonStr);
            Iterator<JSONObject> it = jsonArr.iterator();
            while(it.hasNext()){
                JSONObject json2 = it.next();
                list.add(parseJsonToMap(json2.toString()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static String getUrlString(Map map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        StringBuffer sb=new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if(!entry.getKey().equals("urlSuffix")){
                sb.append("&"+entry.getKey()+"="+entry.getValue());
            }

        }
        return sb.toString();
    }
}

package com.ishansong.action.recommend;

import ch.hsr.geohash.GeoHash;
import com.ishansong.model.other.DeliveryAddresslistFault;
import com.ishansong.model.other.RestGroupBean;
import com.ishansong.model.other.ResultInfo;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by iss on 2017/8/31 上午10:29.
 * <p>
 * 画像服务--常用地址推荐 （elasticsearch 技术实现）
 */
@Controller
@RequestMapping("/recommendAdress")
public class RecommendAdressAction {

    @Resource(name="client")
    private Client client;

    private static final  Logger logger = LogManager.getLogger(RecommendClientAction.class.getName());
    /**
     * 获取常用地址推荐（取件地址）
     * 参数：用户id ,经纬度
     *  lon_location 经度 （longitude）
     *  lat_location 纬度 （Latitude）
     * 返回：常用地址列表（包含经纬度信息）
     * */
    @RequestMapping(value="/getRecommendCollect/{userId}/{lon_location}/{lat_location}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody
    String getRecommendCollect(@PathVariable(value = "userId") String  userId,@PathVariable(value = "lon_location") double  lon_location,@PathVariable(value = "lat_location") double  lat_location) throws Exception {
        logger.info("时间："+new Date()+",开发常用地址推荐（取件地址）访问接口getRecommendCollect，请求内容为 ：userId:"+userId+"=====lon_location:"+lon_location+"========lat_location:"+lat_location);

        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        Object result = null;
        String message = "";

        try {

            //开始时间
            long start = System.currentTimeMillis();
            //判断用户是否存在
            if(getisUser(client,userId)) {
                logger.info("用户存在");

                //开始时间
                long start1 = System.currentTimeMillis();
                //计算出范围的净度和唯独
                int radius_location = 1;// 千米
                SpatialContext geo_location = SpatialContext.GEO;
                Rectangle rectangle_location = geo_location.getDistCalc().calcBoxByDistFromPt(
                        geo_location.makePoint(lon_location, lat_location), radius_location * DistanceUtils.KM_TO_DEG, geo_location, null);
                logger.info("当前的经纬度是=========" + lon_location + "==" + lat_location);
                logger.info("设备当前位置的经度范围：" + rectangle_location.getMinX() + "-" + rectangle_location.getMaxX());// 经度范围
                logger.info("设备当前位置的纬度范围：" + rectangle_location.getMinY() + "-" + rectangle_location.getMaxY());// 纬度范围

                //当前设备经纬度的hash 值(因为计算的值需要作为条件，获取范围，只计算6位的值  6代表： width=1.2km ,height=609.4m)
                GeoHash geoHash_location = GeoHash.withCharacterPrecision(lat_location, lon_location, 5);
                String geocode_location = geoHash_location.toBase32();
                logger.info("当前经纬度的geohash=========" + geocode_location);


                //根据当前时间计算30分钟时间范围

                String dateRangeStr = getDateRange();
                //搜索条件 (时间，经纬度范围)
                RangeQueryBuilder matchQuery_order_time = QueryBuilders.rangeQuery("order_time").gte(dateRangeStr.split(",")[0]).lt(dateRangeStr.split(",")[1]);
                QueryBuilder matchQuery_user_id = QueryBuilders.termQuery("user_id",userId);
                RangeQueryBuilder to_lng_range = QueryBuilders.rangeQuery("from_lng").gte(rectangle_location.getMinX()).lt(rectangle_location.getMaxX());
                RangeQueryBuilder to_lat_range = QueryBuilders.rangeQuery("from_lat").gte(rectangle_location.getMinY()).lt(rectangle_location.getMaxY());
                QueryBuilder matchQuery_geohash = QueryBuilders.wildcardQuery("geo_hash", "*" + geocode_location + "*");
                QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                        .must(to_lat_range)
                        .must(to_lng_range)
                        .must(matchQuery_geohash)
                        .must(matchQuery_order_time)
                        .must(matchQuery_user_id);

                long end1 = System.currentTimeMillis();
                logger.info("1============生成查询取件地址的条件，整个请求花费："+(end1 - start1) + "毫秒");

                //开始时间
                long start2 = System.currentTimeMillis();

                SearchResponse searchResponse = client.prepareSearch("sites_recomm_from_address_allones").setQuery(queryBuilder2).setSize(5000).execute().actionGet();

                long end2 = System.currentTimeMillis();
                logger.info("2============查询取件信息结果，整个请求花费："+(end2 - start2) + "毫秒");

                //开始时间
                long start3 = System.currentTimeMillis();

                // 获取查询的结果集
                SearchHits searchHits = searchResponse.getHits();
                logger.info("总共搜索到" + searchHits.getTotalHits() + "条结果！");

                int i = 0;
                int rowCount = Integer.valueOf(String.valueOf(searchHits.getHits().length));

                //创建时间，用于后续做比较使用
                Calendar now_location = Calendar.getInstance();
                long time_location = now_location.getTimeInMillis();
                Map<String, RestGroupBean> restGroupBeanMap = new HashMap<String, RestGroupBean>();
                for (SearchHit hit : searchHits) {
                    logger.info("String方式打印文档搜索内容:");
                    logger.info(hit.getSourceAsString());
                    Map<String, Object> source = hit.getSource();
                    double from_lng_value = Double.valueOf(source.get("from_lng").toString());
                    double from_lat_value = Double.valueOf(source.get("from_lat").toString());
                    logger.info("经度double=========" + from_lng_value);
                    logger.info("纬度double=========" + from_lat_value);
                    String order_time = source.get("order_time").toString();
                    logger.info("======order_time ========" + order_time);
                    logger.info("======order_time ========" + getRemoveStr(order_time.substring(0, 2)));
                    logger.info("======order_time ========" + getRemoveStr(order_time.substring(2, 4)));

                    String from_address = source.get("from_address").toString();
                    Double address_cnt = Double.valueOf(source.get("address_cnt").toString());

                    Calendar nowNew = Calendar.getInstance();
                    nowNew.set(Calendar.HOUR_OF_DAY, getRemoveStr(order_time.substring(0, 2)));
                    nowNew.set(Calendar.MINUTE, getRemoveStr(order_time.substring(2, 4)));
                    long time = nowNew.getTimeInMillis();

                    //时间差
                    long between_minute = (time_location - time) / (1000 * 60);
                    int between_minute_absolute = Math.abs(Integer.parseInt(String.valueOf(between_minute)));
                    logger.info("=======时间差：" + between_minute_absolute);

                    GeoHash geoHash = GeoHash.withCharacterPrecision(from_lat_value, from_lng_value, 10);
                    String geocode = geoHash.toBase32();
                    SpatialContext geo = SpatialContext.GEO;
                    double distance = geo.calcDistance(geo.makePoint(lon_location, lat_location), geo.makePoint(from_lng_value, from_lat_value)) * DistanceUtils.DEG_TO_KM;

                    //根据时间差和距离以及频次计算权重值
                    double weightNumber = getWeight(distance * 1000, between_minute_absolute, 1);
                    logger.info("========距离：：" + distance * 1000);
                    logger.info("========权重值：" + weightNumber);

                    String mapKey = from_address;
                    RestGroupBean restGroupBean = new RestGroupBean();
                    restGroupBean.setOrder_time(order_time);
                    restGroupBean.setBetween_minute(String.valueOf(between_minute_absolute));
                    restGroupBean.setFrom_lat(String.valueOf(from_lat_value));
                    restGroupBean.setFrom_lng(String.valueOf(from_lng_value));
                    restGroupBean.setFrom_address(from_address);
                    restGroupBean.setDistance(distance);
                    if (restGroupBeanMap.containsKey(mapKey)) {
                        restGroupBean.setAddress_cnt(restGroupBeanMap.get(mapKey).getAddress_cnt() + address_cnt);
                    } else {
                        restGroupBean.setAddress_cnt(address_cnt);
                    }
                    restGroupBeanMap.put(from_address, restGroupBean);


                }

                long end3 = System.currentTimeMillis();
                logger.info("3============得到取件结果第一次循环，整个请求花费："+(end3 - start3) + "毫秒");

                //开始时间
                long start4 = System.currentTimeMillis();

                //循环计算权重值
                int aaa = 0;
                List<RestGroupBean> restGroupBeanList1 = new ArrayList<RestGroupBean>();
                for (Entry<String, RestGroupBean> entry : restGroupBeanMap.entrySet()) {
                    RestGroupBean bean = entry.getValue();
                    bean.setWeightValue(getWeight(Double.valueOf(bean.getDistance()), Double.valueOf(bean.getBetween_minute()), bean.getAddress_cnt()));
                    restGroupBeanList1.add(bean);
                    System.out.println("index:" + aaa + "       " + bean.getFrom_address() + "        " + bean.getAddress_cnt());
                    aaa++;
                }
                //根据权重值进行排序处理
                Collections.sort(restGroupBeanList1);


                long end4 = System.currentTimeMillis();
                logger.info("4============得到取件结果第二次循环，整个请求花费："+(end4 - start4) + "毫秒");


                //开始时间
                long start5 = System.currentTimeMillis();

                List<RestGroupBean> restGroupBeanList2 = new ArrayList<RestGroupBean>();
                //获取权重值最大的前十条进行返回
                int bbb = 0;
                for (RestGroupBean restGroupBean : restGroupBeanList1) {
                    if (bbb == 10) {
                        break;
                    }
                    System.out.println("weightValue:" + restGroupBean.getWeightValue());
                    restGroupBeanList2.add(restGroupBean);
                    bbb++;
                }
                if(restGroupBeanList2.size() > 0){
                    result =restGroupBeanList2;
                    message = "成功";
                }else{
                    code = "3";
                    message = "无结果";
                }

                long end5 = System.currentTimeMillis();
                logger.info("5============排序top10生成结果，整个请求花费："+(end5 - start5) + "毫秒");
            }else{
                logger.info("用户不存在");
                message ="用户不存在";
                code = "2";
            }
            long end = System.currentTimeMillis();
            logger.info("整个请求花费："+(end - start) + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
            e.printStackTrace();
            message ="失败";
            code = "1";
        }
        resultInfo.setMessage(message);
        resultInfo.setResult(result);
        resultInfo.setCode(code);
        JSONObject json = JSONObject.fromObject(resultInfo);
        logger.info(json.toString());
        String returnStr = json.toString();
        logger.info("时间："+new Date()+",开发常用地址推荐（取件地址）访问接口getRecommendCollect，，返回内容为 ："+returnStr);
        return returnStr;
    }

    /**
     * 获取常用地址推荐（送件地址）
     * 参数：用户id ,经纬度
     *  lon_location 经度 （longitude）
     *  lat_location 纬度 （Latitude）
     * 返回：常用地址列表（包含经纬度信息）
     * */
    @RequestMapping(value="/getRecommendDelivery/{userId}/{collect_lon_location}/{collect_lat_location}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public  @ResponseBody
    String getRecommendDelivery(@PathVariable(value = "userId") String  userId,@PathVariable(value = "collect_lon_location") double  collect_lon_location,@PathVariable(value = "collect_lat_location") double  collect_lat_location) throws Exception {
        logger.info("时间：" + new Date() + ",开发常用地址推荐（送件地址）访问接口getRecommendDelivery ，请求内容为 ：userId:" + userId + "=====collect_lon_location:" + collect_lon_location + "========collect_lat_location:" + collect_lat_location);

        ResultInfo resultInfo = new ResultInfo();
        String code = "0";
        String message = "";
        Object result = null;
        try{
            //判断用户是否存在
            if(getisUser(client,userId)) {
                logger.info("用户存在");

                //查询条件
                QueryBuilder matchQuery_user_id = QueryBuilders.termQuery("user_id",userId);
                QueryBuilder matchQuery_from_lat = QueryBuilders.termQuery("from_lat",collect_lat_location);
                QueryBuilder matchQuery_from_lng = QueryBuilders.termQuery("from_lng",collect_lon_location);

                QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(matchQuery_user_id)
                        .must(matchQuery_from_lat)
                        .must(matchQuery_from_lng);

                SearchResponse searchResponse = client.prepareSearch("user_realation_sites").setQuery(queryBuilder).setSize(5000).execute().actionGet();
                // 获取查询的结果集
                SearchHits searchHits = searchResponse.getHits();

                int rowCount = Integer.valueOf(String.valueOf(searchHits.getHits().length));
                logger.info("rowcount:"+rowCount);
                String[][] array = new String[rowCount][6];
                int i =0;
                for (SearchHit hit : searchHits) {
                    logger.info("String方式打印文档搜索内容:");
                    logger.info(hit.getSourceAsString());
                    Map<String, Object> source = hit.getSource();
                    double to_lat_value = Double.valueOf(source.get("to_lat").toString());
                    double to_lng_value = Double.valueOf(source.get("to_lng").toString());

                    String to_address= source.get("to_address").toString();
                    String to_address_detail = source.get("to_address_detail").toString();
                    Double address_cnt_value = Double.valueOf(source.get("address_cnt").toString());
                    array[i][0]=String.valueOf(i);
                    array[i][1]=String.valueOf(to_lat_value);
                    array[i][2]=String.valueOf(to_lng_value);
                    array[i][3]=String.valueOf(to_address);
                    array[i][4]=String.valueOf(to_address_detail);
                    array[i][5]=String.valueOf(address_cnt_value);
                    i++;
                }

                //根据频次进行排序
                array =getOrder(array);
                List<DeliveryAddresslistFault> relReceiveFaultList = showArray(array);
                if(relReceiveFaultList.size() > 0){
                    result = relReceiveFaultList;
                    message = "成功";
                }else{
                    logger.info("无结果");
                    message ="无结果";
                    code = "3";
                }
            }else{
                logger.info("用户不存在");
                message ="用户不存在";
                code = "2";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("异常信息："+e.getMessage());
            System.out.println("异常内容：");
            e.printStackTrace();
            message ="失败";
            code = "1";
        }

        resultInfo.setMessage(message);
        resultInfo.setCode(code);
        resultInfo.setResult(result);
        JSONObject json = JSONObject.fromObject(resultInfo);
        logger.info(json.toString());
        String returnStr = json.toString();
        logger.info("时间："+new Date()+",开发常用地址推荐（送件地址）访问接口getRecommendDelivery，返回内容为 ："+returnStr);
        return returnStr;

    }




    // 获取附近的人
    public static void testGetNearbyPeople(TransportClient client, String index, String type, double lat, double lon) {
        SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
        srb.setFrom(0).setSize(1000);//1000人
        // lon, lat位于谦的坐标，查询距离于谦1米到1000米
        GeoDistanceQueryBuilder location1 = QueryBuilders.geoDistanceQuery("location").point(lat,lon).distance(100, DistanceUnit.METERS);
        srb.setPostFilter(location1);
        // 获取距离多少公里 这个才是获取点与点之间的距离的
        GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location",lat,lon);
        sort.unit(DistanceUnit.METERS);
        sort.order(SortOrder.ASC);
        sort.point(lat,lon);
        srb.addSort(sort);

        SearchResponse searchResponse = srb.execute().actionGet();

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        // 搜索耗时
        Float usetime = searchResponse.getTookInMillis() / 1000f;
        logger.info("于谦附近的人(" + hits.getTotalHits() + "个)，耗时("+usetime+"秒)：");
        for (SearchHit hit : searchHists) {
            String name = (String) hit.getSource().get("name");
            List<Double> location = (List<Double>)hit.getSource().get("location");
            // 获取距离值，并保留两位小数点
            BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
            Map<String, Object> hitMap = hit.getSource();
            // 在创建MAPPING的时候，属性名的不可为geoDistance。
            hitMap.put("geoDistance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN));
            logger.info(name+"的坐标："+location + "他距离于谦" + hit.getSource().get("geoDistance") + DistanceUnit.METERS.toString());
        }

    }





    /**
     * 根据用户id 判断用户是否下过单
     * @param client
     * @param userId
     * @return
     */
    public  boolean getisUser(Client client, String userId){
        boolean bl = false;
        //开始时间
        long start = System.currentTimeMillis();
        GetResponse getFields = client.prepareGet("sites_recomm_user", "sites_recomm_user", userId).execute().actionGet();
        if(getFields!= null){
            logger.info(getFields.getId());
            logger.info(getFields.getSourceAsString());
            bl = true;
        }
        //结束时间
        long end = System.currentTimeMillis();
        logger.info("0============判断用户是否下过单，整个请求花费："+(end - start) + "毫秒");

        return bl;

    }


    /**
     * 补充时间位数
     */
    public  String getDoubleStr(int str){
        String s = String.valueOf(str);
        if(s.length()==1){
            s = "0"+s;
        }
        return s;
    }


    /**
     * 若时间前面有0 则去掉
     */
    public  int getRemoveStr(String str){
        String firstStr = str.substring(0,1);
        if(firstStr.equals("0")){
            str = str.substring(1,2);
        }

        return Integer.valueOf(str);
    }




    /**
     *  二维数组排序，比较array[][5]的值，返回二维数组
     */
    public  String[][] getOrder(String[][] array){
        for (int j = 0; j < array.length ; j++) {
            for (int bb = 0; bb < array.length - 1; bb++) {
                String[] ss;
                double a1=Double.valueOf(array[bb][5]);  //转化成double型比较大小
                double a2=Double.valueOf(array[bb+1][5]);
                if (a1<a2) {
                    ss = array[bb];
                    array[bb] = array[bb + 1];
                    array[bb + 1] = ss;

                }
            }
        }
        return array;
    }



    /**
     * 打印数组
     */
    public static List<DeliveryAddresslistFault> showArray(String[][] array){
        List<DeliveryAddresslistFault> relReceiveFaultList = new ArrayList<DeliveryAddresslistFault>();
        for(int a=0;a<array.length;a++){
            if (a == 10){
                break;
            }
            DeliveryAddresslistFault relChecklistFault = new DeliveryAddresslistFault();
            relChecklistFault.setIndex(array[a][0]);
            relChecklistFault.setTo_lat_value(array[a][1]);
            relChecklistFault.setTo_lng_value(array[a][2]);
            relChecklistFault.setTo_address(array[a][3]);
            relChecklistFault.setTo_address_detail(array[a][4]);
            relChecklistFault.setAddress_cnt_value(array[a][5]);
            relReceiveFaultList.add(relChecklistFault);
            for(int j=0;j<array[0].length;j++)
                System.out.print(array[a][j]+" ");
            System.out.println();
        }
        return  relReceiveFaultList;
    }


    /**
     *
     *
     *根据当前时间计算日期范围
     *
     **/
    public  String  getDateRange(){
        Calendar now = Calendar.getInstance();
        String hourStr = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
        String minuteStr = getDoubleStr(now.get(Calendar.MINUTE));
        logger.info("当前时间小时+分钟：："+hourStr+minuteStr);
        now.add(Calendar.MINUTE,30);
        String hourStrAfter = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
        String minuteStrAfter = getDoubleStr(now.get(Calendar.MINUTE));
        logger.info("半个小时之后的时间小时+分钟：："+hourStrAfter+minuteStrAfter);
        now.add(Calendar.MINUTE,-60);
        String hourStrBefore = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
        String minuteStrBefore = getDoubleStr(now.get(Calendar.MINUTE));
        logger.info("半个小时之前的时间小时+分钟：："+hourStrBefore+minuteStrBefore);
        return hourStrBefore+minuteStrBefore+","+hourStrAfter+minuteStrAfter;

    }

    /**
     *
     *根据距离和相差时间（分钟）以及 频次 计算权重值
     *
     **/
    public  double getWeight(double distance ,double minute, double frequency){
        //距离(米)的权重值
        double distanceWeight ;

        if(distance>0 && distance <100){
            distanceWeight =0.30;
        }else if(distance >=100 &&  distance < 200){
            distanceWeight = 0.25;
        }else if(distance >=200 && distance < 500){
            distanceWeight = 0.20;
        }else if (distance >=500  && distance < 800){
            distanceWeight = 0.15;
        }else if (distance >= 800 ){
            distanceWeight = 0.10;
        }else{
            distanceWeight = 0;
        }
        //时间
        double dateWeight  ;
        if(minute >0 && minute <5){
            dateWeight = 0.27;
        }else if(minute >=5 && minute <10){
            dateWeight = 0.23;
        }else if(minute >=10 && minute <15){
            dateWeight = 0.20;
        }else if(minute >=15 && minute <20){
            dateWeight = 0.15;
        }else if(minute >=20 && minute <25){
            dateWeight = 0.10;
        }else if(minute >=25 ){
            dateWeight = 0.05;
        }else{
            dateWeight = 0;
        }
        //频次
        double frequencyWeight ;
        if(frequency > 0 && frequency < 3){
            frequencyWeight  = 0.20;
        }else if(frequency >=3 && frequency <8){
            frequencyWeight = 0.35;
        }else if (frequency >=8 ){
            frequencyWeight = 0.45;
        }else {
            frequencyWeight = 0;
        }

        return distanceWeight * 0.6 + dateWeight * 0.3+ frequencyWeight * 0.1;
    }





}

package com.ishansong.action.recommend;

import ch.hsr.geohash.GeoHash;
import com.ishansong.model.other.CollectAddresslistFault;
import com.ishansong.model.other.RestGroupBean;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.*;

/**
 * Created by yangguoliang on 2017/8/31 上午10:33.
 * <p>
 *  测试版本 ---连接elasticsearch 做查询处理
 */
public class RecommendClientAction {

//    private static final Logger logger = LogManager.getLogger(RecommendClientAction.class.getName());


    public static void main(String [] args){
        try {


                //设置集群名称
                Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
                //创建client
                TransportClient client = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), Integer.valueOf(9300)));

                List<DiscoveryNode> nodeList =  client.connectedNodes();
                for (DiscoveryNode node : nodeList){
                    System.out.println(node.getHostAddress());
                }

              // 移动设备经纬度
                double lon_location = 116.3125333347639, lat_location = 39.93452;

                //计算出范围的净度和唯独
                // 千米
                int radius_location = 20;


                SpatialContext geo_location = SpatialContext.GEO;
                Rectangle rectangle_location = geo_location.getDistCalc().calcBoxByDistFromPt(
                        geo_location.makePoint(lon_location, lat_location), radius_location * DistanceUtils.KM_TO_DEG, geo_location, null);
                System.out.println("当前的经纬度是========="+lon_location +"=="+lat_location);
                System.out.println("设备当前位置的经度范围："+rectangle_location.getMinX() + "-" + rectangle_location.getMaxX());// 经度范围
                System.out.println("设备当前位置的纬度范围："+rectangle_location.getMinY() + "-" + rectangle_location.getMaxY());// 纬度范围

                //当前设备经纬度的hash 值
                GeoHash geoHash_location = GeoHash.withCharacterPrecision(lat_location, lon_location, 4);
                String geocode_location = geoHash_location.toBase32();
                System.out.println("当前经纬度的geohash========="+geocode_location);

            // group 操作
//            TermsAggregationBuilder tb = AggregationBuilders.terms("group_name").field("from_address");
//            SumAggregationBuilder sab = AggregationBuilders.sum("sum_count").field("address_cnt");
//            tb.subAggregation(sab);
//            addAggregation(tb)


            //根据当前时间计算30分钟时间范围
                String dateRangeStr =  getDateRange();
                //搜索条件 (时间，经纬度范围) [gt :: 大于,gte:: 大于等于,lt :: 小于,lte:: 小于等于 ]
                RangeQueryBuilder matchQuery_order_time = QueryBuilders.rangeQuery("order_time").gte( dateRangeStr.split(",")[0]).lte( dateRangeStr.split(",")[1]);
//                QueryBuilder matchQuery_user_id = QueryBuilders.matchQuery("user_id","503571");
                RangeQueryBuilder to_lng_range = QueryBuilders.rangeQuery("from_lng").gte(rectangle_location.getMinX()).lte(rectangle_location.getMaxX());
                RangeQueryBuilder to_lat_range = QueryBuilders.rangeQuery("from_lat").gte(rectangle_location.getMinY()).lte(rectangle_location.getMaxY());
                QueryBuilder matchQuery_geohash = QueryBuilders.wildcardQuery("geo_hash","*"+geocode_location+"*");
                QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                        .must(to_lat_range)
                        .must(to_lng_range)
                        .must(matchQuery_geohash)
                        .must(matchQuery_order_time);
//                        .must(matchQuery_user_id);
                SearchResponse searchResponse = client.prepareSearch("sites_recomm_from_address_allones").setQuery(queryBuilder2).setSize(1000).execute().actionGet();

                // 获取查询的结果集
                SearchHits searchHits = searchResponse.getHits();
                System.out.println("总共搜索到"+searchHits.getTotalHits()+"条结果！");

            //开始时间
            long start = System.currentTimeMillis();
                int i=0;
                int rowCount = Integer.valueOf(String.valueOf(searchHits.getHits().length));

                String[][] array = new String[rowCount][7];
                //创建时间，用于后续做比较使用
                 Calendar now_location = Calendar.getInstance();
                 long time_location = now_location.getTimeInMillis();
                 Map<String,RestGroupBean> restGroupBeanMap = new HashMap<String,RestGroupBean>();
                for (SearchHit hit :searchHits){
                    System.out.println("String方式打印文档搜索内容:");
                    System.out.println(hit.getSourceAsString());
                    Map<String, Object> source = hit.getSource();

                    double from_lng_value = Double.valueOf(source.get("from_lng").toString());
                    double from_lat_value = Double.valueOf(source.get("from_lat").toString());
                    System.out.println("经度double========="+from_lng_value);
                    System.out.println("纬度double========="+from_lat_value);

                    String order_time =  source.get("order_time").toString();
                    System.out.println("======order_time ========"+order_time);
                    System.out.println("======order_time ========"+getRemoveStr(order_time.substring(0,2)));
                    System.out.println("======order_time ========"+getRemoveStr(order_time.substring(2,4)));

                    String from_address = source.get("from_address").toString();
                    Double address_cnt = Double.valueOf(source.get("address_cnt").toString());

                    Calendar nowNew = Calendar.getInstance();
                    nowNew.set(Calendar.HOUR_OF_DAY,getRemoveStr(order_time.substring(0,2)));
                    nowNew.set(Calendar.MINUTE,getRemoveStr(order_time.substring(2,4)));
                    long time = nowNew.getTimeInMillis();

                    //时间差
                    long between_minute = (time_location-time)/(1000*60);
                    System.out.println("=======时间差(原始值)："+ between_minute);

                    int between_minute_absolute  = Math.abs(Integer.parseInt(String.valueOf(between_minute)));
                    System.out.println("=======时间差："+ between_minute_absolute);

                    GeoHash geoHash = GeoHash.withCharacterPrecision(from_lat_value, from_lng_value, 10);
                    String geocode = geoHash.toBase32();
                    SpatialContext geo = SpatialContext.GEO;
                    double distance = geo.calcDistance(geo.makePoint(lon_location, lat_location), geo.makePoint(from_lng_value, from_lat_value)) * DistanceUtils.DEG_TO_KM;

                    //根据时间差和距离计算权重值
                    double weightNumber = getWeight(distance*1000, between_minute_absolute,1);
                    System.out.println("========距离：："+distance*1000);
                    System.out.println("========权重值："+weightNumber);

                    array[i][0]=String.valueOf(i);
                    array[i][1]=geocode;
                    array[i][2]=String.valueOf(distance);
                    array[i][3]=String.valueOf(weightNumber);
                    array[i][4]=String.valueOf(from_lat_value);
                    array[i][5]=String.valueOf(from_lng_value);
                    array[i][6]=from_address;



                    String mapKey = from_address;
                    RestGroupBean restGroupBean = new RestGroupBean();
                    restGroupBean.setOrder_time(order_time);
                    restGroupBean.setBetween_minute(String.valueOf(between_minute_absolute));
                    restGroupBean.setFrom_lat(String.valueOf(from_lat_value));
                    restGroupBean.setFrom_lng(String.valueOf(from_lng_value));
                    restGroupBean.setFrom_address(from_address);
                    restGroupBean.setDistance(distance);
                    if(restGroupBeanMap.containsKey(mapKey)){
                        restGroupBean.setAddress_cnt(restGroupBeanMap.get(mapKey).getAddress_cnt()+address_cnt);
                        restGroupBeanMap.put(from_address,restGroupBean);
                    }else{
                        restGroupBean.setAddress_cnt(address_cnt);
                        restGroupBeanMap.put(from_address,restGroupBean);
                    }
                    i++;
                }
                if(array!= null ){
                    array =getOrder(array);
                    List<CollectAddresslistFault> relReceiveFaultList = showArray(array);
                    net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(relReceiveFaultList);
                    System.out.printf("打印结果数据："+jsonArray.toString());
                }

                int aaa = 0;
                List<RestGroupBean> restGroupBeanList1 = new ArrayList<RestGroupBean>();
            for (Map.Entry<String,RestGroupBean> entry : restGroupBeanMap.entrySet()) {
                RestGroupBean bean= entry.getValue();
                bean.setWeightValue(getWeight(Double.valueOf(bean.getDistance()),Double.valueOf(bean.getBetween_minute()),bean.getAddress_cnt()));
                restGroupBeanList1.add(bean);
                System.out.println("index:"+aaa+"       "+bean.getFrom_address()+ "        "+bean.getAddress_cnt());
                aaa++;
            }

            Collections.sort(restGroupBeanList1);
            List<RestGroupBean> restGroupBeanList2 = new ArrayList<RestGroupBean>();

            int bbb = 0;
            for (RestGroupBean restGroupBean :restGroupBeanList1){
                if(bbb == 20){
                    break;
                }
                net.sf.json.JSONArray jsonArrayRestStr = net.sf.json.JSONArray.fromObject(restGroupBean);
                System.out.println("结果数据====================："+jsonArrayRestStr.toString());
                restGroupBeanList2.add(restGroupBean);
                bbb++;
            }



            System.out.println("*****************************************************************************************************************************");
                System.out.println("*****************************************************************************************************************************");

                // 移动设备经纬度
                double lon = 116.312528, lat = 39.983733;
                GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lon, 10);
                // 当前
                System.out.println(geoHash.toBase32());
                GeoHash[] adjacent = geoHash.getAdjacent();
                for (GeoHash hash : adjacent) {
                    System.out.println(hash.toBase32());
                }

                System.out.println("*****************************************************************************************************************************");
                System.out.println("*****************************************************************************************************************************");
                long end = System.currentTimeMillis();
               System.out.println("整个请求花费："+(end - start) + "毫秒");

                client.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 补充时间位数
     */
    public static String getDoubleStr(int str){
        String s = String.valueOf(str);
        if(s.length()==1){
            s = "0"+s;
        }
        return s;
    }


    /**
     * 若时间前面有0 则去掉
     */
    public static int getRemoveStr(String str){
        String firstStr = str.substring(0,1);
        if(firstStr.equals("0")){
            str = str.substring(1,2);
        }

        return Integer.valueOf(str);
    }




    /**
     *  二维数组排序，比较array[][3]的值，返回二维数组
     */
    public static String[][] getOrder(String[][] array){
        for (int j = 0; j < array.length ; j++) {
            for (int bb = 0; bb < array.length - 1; bb++) {
                String[] ss;
                double a1=Double.valueOf(array[bb][3]);  //转化成double型比较大小
                double a2=Double.valueOf(array[bb+1][3]);
                if (a1>a2) {
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
    public static List<CollectAddresslistFault> showArray(String[][] array){
        List<CollectAddresslistFault> relReceiveFaultList = new ArrayList<CollectAddresslistFault>();
        for(int a=0;a<array.length;a++){
            CollectAddresslistFault relChecklistFault = new CollectAddresslistFault();
            relChecklistFault.setIndex(array[a][0]);
            relChecklistFault.setGeocode(array[a][1]);
            relChecklistFault.setDistance(array[a][2]);
            relChecklistFault.setWeightNumber(array[a][3]);
            relChecklistFault.setFrom_lat_value(array[a][4]);
            relChecklistFault.setFrom_lng_value(array[a][5]);
            relChecklistFault.setFrom_address(array[a][6]);
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
    public static String  getDateRange(){
        Calendar now = Calendar.getInstance();
        String hourStr = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
        String minuteStr = getDoubleStr(now.get(Calendar.MINUTE));
        System.out.println("当前时间小时+分钟：："+hourStr+minuteStr);
        now.add(Calendar.MINUTE,30);
        String hourStrAfter = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
        String minuteStrAfter = getDoubleStr(now.get(Calendar.MINUTE));
        System.out.println("半个小时之后的时间小时+分钟：："+hourStrAfter+minuteStrAfter);
        now.add(Calendar.MINUTE,-60);
        String hourStrBefore = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
        String minuteStrBefore = getDoubleStr(now.get(Calendar.MINUTE));
        System.out.println("半个小时之前的时间小时+分钟：："+hourStrBefore+minuteStrBefore);
        System.out.println("时间范围取整数值的结果为："+hourStrBefore+getIntegerNumber(minuteStrBefore)+"-"+hourStrAfter+getIntegerNumber(minuteStrAfter));


        return hourStrBefore+minuteStrBefore+","+hourStrAfter+minuteStrAfter;

    }



    /**
     * 将分钟值的最后以为替换为0,取整数
     *
     * */
    public static String getIntegerNumber(String str){

        if(str.length() == 2){
            str = str.substring(0,1)+"0";
        }
        return str;

    }

    /**
     *
     *根据距离和相差时间（分钟）以及 频次 计算权重值
     *
     **/
    public static double getWeight(double distance ,double minute, double frequency){
        //距离(米)的权重值
        double distanceWeight ;

        if(distance>0 && distance <100){
            distanceWeight =0.30;
        }else if(distance >=1000 &&  distance < 2000){
            distanceWeight = 0.25;
        }else if(distance >=2000 && distance < 5000){
            distanceWeight = 0.20;
        }else if (distance >=5000  && distance < 8000){
            distanceWeight = 0.15;
        }else if (distance >= 8000 ){
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

//package com.ishansong.action.recommend;
//
//import ch.hsr.geohash.GeoHash;
//import com.spatial4j.core.context.SpatialContext;
//import com.spatial4j.core.distance.DistanceUtils;
//import com.spatial4j.core.shape.Rectangle;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.cluster.node.DiscoveryNode;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
//import java.net.InetAddress;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by yangguoliang on 2017/8/31 上午10:33.
// * <p>
// *  客户端
// */
//public class demo {
//
////    private static final Logger logger = LogManager.getLogger(RecommendClientAction.class.getName());
//
//
//    public static void main(String [] args){
//        try {
//
//            //设置集群名称
//            Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//            //创建client
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), Integer.valueOf(9300)));
//
//            List<DiscoveryNode> nodeList =  client.connectedNodes();
//            for (DiscoveryNode node : nodeList){
//                System.out.println(node.getHostAddress());
//            }
//
//
////            if(RecommendAdressAction.getisUser(client,"2613280")){
////                System.out.println("用户存在");
//
//            Calendar now = Calendar.getInstance();
//            String hourStr = getDoubleStr(now.get(Calendar.HOUR_OF_DAY));
//            String minuteStr = getDoubleStr(now.get(Calendar.MINUTE));
//            hourStr="17";
//            minuteStr = "09";
//            System.out.println("当前时间小时："+hourStr);
//            System.out.println("当前时间分钟："+minuteStr);
//            System.out.println("当前时间小时+分钟：："+hourStr+minuteStr);
//
//            // 移动设备经纬度
//            double lon_location = 116.3125333347639, lat_location = 39.98355521792821;
//
//
//
//            //计算出范围的净度和唯独
//            // 千米
//            int radius_location = 1;
//
//            SpatialContext geo_location = SpatialContext.GEO;
//            Rectangle rectangle_location = geo_location.getDistCalc().calcBoxByDistFromPt(
//                    geo_location.makePoint(lon_location, lat_location), radius_location * DistanceUtils.KM_TO_DEG, geo_location, null);
//            System.out.println("设备当前位置的经度范围："+rectangle_location.getMinX() + "-" + rectangle_location.getMaxX());// 经度范围
//            System.out.println("设备当前位置的纬度范围："+rectangle_location.getMinY() + "-" + rectangle_location.getMaxY());// 纬度范围
//
//
//
//            //搜索条件
//            QueryBuilder matchQuery = QueryBuilders.matchQuery("order_time",hourStr+minuteStr);
//            HighlightBuilder hiBuilder=new HighlightBuilder();
//            hiBuilder.preTags("<h2>");
//            hiBuilder.postTags("</h2>");
//            hiBuilder.field("order_time");
//
//
//
//
//            System.out.println("*****************************************************************************************************************************");
//            System.out.println("*****************************************************************************************************************************");
//
//            RangeQueryBuilder to_lng_range = QueryBuilders.rangeQuery("to_lng").gte(116.4444444).lt(117);
//            RangeQueryBuilder to_lat_range = QueryBuilders.rangeQuery("to_lat").gte(39.4444444).lt(40);
//            QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
//                    .must(to_lat_range)
//                    .must(to_lng_range)
//                    .must(matchQuery);
//            SearchResponse searchResponse2 = client.prepareSearch("sites_recomm_from_address_more").setQuery(queryBuilder2).highlighter(hiBuilder).execute().actionGet();
//            // 获取查询的结果集
//            SearchHits searchHits2 = searchResponse2.getHits();
//            System.out.println("总共搜索到"+searchHits2.getTotalHits()+"条结果！");
//
//            int i=0;
//            int rowCount = Integer.valueOf(String.valueOf(searchHits2.getHits().length));
//            String[][] array = new String[rowCount][3];
//            for (SearchHit hit :searchHits2){
//                System.out.println("String方式打印文档搜索内容:");
//                System.out.println(hit.getSourceAsString());
//                Map<String, Object> source = hit.getSource();
//                double to_lng_value = Double.valueOf(source.get("to_lng").toString());
//                double to_lat_value = Double.valueOf(source.get("to_lat").toString());
//                System.out.println("经度double========="+to_lng_value);
//                System.out.println("纬度double========="+to_lat_value);
//
//
//                //根据地理坐标，生成geohash编码
//                GeoHash geoHash = GeoHash.withCharacterPrecision(to_lat_value, to_lng_value, 10);
//                String geocode = geoHash.toBase32();
//
//                SpatialContext geo = SpatialContext.GEO;
//                double distance = geo.calcDistance(geo.makePoint(lon_location, lat_location), geo.makePoint(to_lng_value, to_lat_value)) * DistanceUtils.DEG_TO_KM;
//                array[i][0]=String.valueOf(i);
//                array[i][1]=geocode;
//                array[i][2]=String.valueOf(distance);
//                i++;
//
//            }
//            if(array!= null ){
//                array =getOrder(array);
//                showArray(array);
//            }
//
//            System.out.println("当前的经纬度是========="+lon_location +"=="+lat_location);
//            GeoHash geoHash_location = GeoHash.withCharacterPrecision(lat_location, lon_location, 10);
//            String geocode_location = geoHash_location.toBase32();
//            System.out.println("当前经纬度的geohash========="+geocode_location);
//
//
//
//            System.out.println("*****************************************************************************************************************************");
//            System.out.println("*****************************************************************************************************************************");
//                   /*------------------距离计算start-------------------------*/
//
//
//            // 移动设备经纬度
//            double lon1 = 116.3125333347639, lat1 = 39.98355521792821;
//            // 商户经纬度
//            double lon2 = 116.312528, lat2 = 39.983733;
//            SpatialContext geo = SpatialContext.GEO;
//            double distance = geo.calcDistance(geo.makePoint(lon1, lat1), geo.makePoint(lon2, lat2)) * DistanceUtils.DEG_TO_KM;
//            System.out.println(distance*1000);// KM
//
//
//
//
//            // 移动设备经纬度
//            double lon = 116.312528, lat = 39.983733;
//            GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lon, 10);
//            // 当前
//            System.out.println(geoHash.toBase32());
//            GeoHash[] adjacent = geoHash.getAdjacent();
//            for (GeoHash hash : adjacent) {
//                System.out.println(hash.toBase32());
//            }
//
//            System.out.println("*****************************************************************************************************************************");
//            System.out.println("*****************************************************************************************************************************");
//
//
//
//            //搜索数据
//            SearchResponse searchResponse = client.prepareSearch("sites_recomm_from_address_more").setQuery(matchQuery).highlighter(hiBuilder).execute().actionGet();
//            // 获取查询的结果集
//            SearchHits searchHits = searchResponse.getHits();
//            System.out.println("总共搜索到"+searchHits.getTotalHits()+"条结果！");
//            for (SearchHit hit :searchHits){
//                System.out.println("String方式打印文档搜索内容:");
//                System.out.println(hit.getSourceAsString());
//                System.out.println("Map方式打印高亮内容");
//                System.out.println(hit.getHighlightFields());
//                System.out.println(hit.getIndex());
//                Map<String, Object> source = hit.getSource();
//                System.out.println("经度========="+source.get("from_lng"));
//                System.out.println("纬度========="+source.get("from_lat"));
//                double to_lng_value = Double.valueOf(source.get("from_lng").toString());
//                double to_lat_value = Double.valueOf(source.get("from_lat").toString());
//                System.out.println("经度double========="+to_lng_value);
//                System.out.println("纬度double========="+to_lat_value);
//            }
//
//
//
//            // 移动设备经纬度
//            double lon4 = 116.312528, lat4 = 39.983733;
//            // 千米
//            int radius = 1;
//
//            SpatialContext geo2 = SpatialContext.GEO;
//            Rectangle rectangle = geo2.getDistCalc().calcBoxByDistFromPt(
//                    geo.makePoint(lon4, lat4), radius * DistanceUtils.KM_TO_DEG, geo2, null);
//            System.out.println(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围
//            System.out.println(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围
//
//
//
//
//
//
//
//
//
//
//
//            String index = "sites_recomm_from_address_more";
//            String type = "sites_recomm_from_address_more";
//
//            double lat3 = 39.929986;
//            double lon3 = 116.395645;
//            long start = System.currentTimeMillis();
////                    testGetNearbyPeople(client, index, type, lat3, lon3);
//            long end = System.currentTimeMillis();
//            System.out.println((end - start) + "毫秒");
//
//
//                    /*------------------距离计算end-------------------------*/
//
//
//
////            }else{
////                System.out.println("用户不存在");
////            }
//
//
//
//            client.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    /**
//     * 补充时间位数
//     */
//    public static String getDoubleStr(int str){
//        String s = String.valueOf(str);
//        if(s.length()==1){
//            s = "0"+s;
//        }
//        return s;
//
//    }
//
//
//
//    /**
//     *  二维数组排序，比较array[][2]的值，返回二维数组
//     */
//    public static String[][] getOrder(String[][] array){
//        for (int j = 0; j < array.length ; j++) {
//            for (int bb = 0; bb < array.length - 1; bb++) {
//                String[] ss;
//                double a1=Double.valueOf(array[bb][2]);  //转化成int型比较大小
//                double a2=Double.valueOf(array[bb+1][2]);
//                if (a1>a2) {
//                    ss = array[bb];
//                    array[bb] = array[bb + 1];
//                    array[bb + 1] = ss;
//
//                }
//            }
//        }
//        return array;
//    }
//
//    /**
//     * 打印数组
//     */
//    public static void showArray(String[][] array){
//        for(int a=0;a<array.length;a++){
//            for(int j=0;j<array[0].length;j++)
//                System.out.print(array[a][j]+" ");
//            System.out.println();
//        }
//    }
//
//}

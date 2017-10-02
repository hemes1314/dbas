package test;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangguoliang on 2017/6/22 下午5:30.
 * <p>
 * 测试接口的加密方式
 */
public class ResteasyAPITest {

//    private  static final String targetURL = "http://localhost:8080/rest/demo/";
//    private  static final String targetURL = "http://localhost:8089/rest/TravelWayService/getremark1/";

//    private  static final String targetURL = "http://localhost:8199/demo2/date/getdate";
//    private  static final String targetURL = "http://probe.bingex.com/bdpis/rest/TravelWayService/getremark2/";
//    private  static final String targetURL = "http://100.114.248.17/bdpis/rest/TravelWayService/getremark2/";
//    private  static final String targetURL = "http://serviceapi.bingex.com/bdpis/rest/TravelWayService/getremark2/";
    private  static final String targetURL = "http://serviceapi1.ishansong.com/bdpis/rest/TravelWayService/getremark2/";

    public  static void main (String[] args) throws Exception {

//        System.out.println(restfulGet(AESUtils.Encrypt("水电费",AESUtils.cKey)));
//        System.out.println(restfulGet(AESUtils.Encrypt("汽车，摩托车都可",AESUtils.cKey)));
//        System.out.println(restfulGet(AESUtils.Encrypt("除了摩托车",AESUtils.cKey)));
//        System.out.println(restfulGet(AESUtils.Encrypt("汽车,摩托车,公共汽车都可以",AESUtils.cKey)));
//        System.out.println(restfulGet(AESUtils.Encrypt("不要自行车",AESUtils.cKey)));


//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        for (int i = 0; i <= 10; i++) {
//            System.out.println("start~~~~" + df.format(new Date()));
//            System.out.println("这是第" + i + "次循环程序执行～～～～～～～～～～～～");
//            System.out.println(restfulGet("汽车，摩托车都可"));
////            System.out.println(restfulGet("不要自行车"));
//            System.out.println("end~~~~" + df.format(new Date()));
//        }
        System.out.println(restfulGet("不要汽车"));
//        System.out.println(restfulGet("除了摩托车"));
//        System.out.println(restfulGet("汽车,摩托车,公共汽车都可以"));

//        System.out.printf(restfulGet("FV4F%2bj6avTlC6%2biFeNPr9uo7S8s6G8rkX5kI%2f%2fW74V4%3d"));
//        System.out.printf(restfulGet("FV4F+j6avTlC6+iFeNPr9uo7S8s6G8rkX5kI//W74V4="));


//        // 锁住所有线程，等待并发执行
//        final CountDownLatch begin = new CountDownLatch(1);
//        final ExecutorService exec = Executors.newFixedThreadPool(10);
//
//        for (int index = 0; index < 100; index++) {
//            final int NO = index + 1;
//            Runnable run = new Runnable() {
//                public void run() {
//                    try {
//                        // 等待，所有一起执行
//                        begin.await();
//                        //*****执行程序去********//
//                        restfulGet("除了摩托车");
//                      System.out.println(restfulGet("除了摩托车"));
//                      System.out.println(restfulGet("汽车,摩托车,公共汽车都可以"));
//                        //*****执行程序去********//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (CloneNotSupportedException e) {
//                        e.printStackTrace();
//                    } finally {
//                    }
//                }
//            };
//            exec.submit(run);
//
//
//        }

    }




    public static String restfulGet(String str) throws CloneNotSupportedException {
        String result = "";
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            // 创建httpget.
            HttpGet httpget = new HttpGet("" + targetURL + URLEncoder.encode(str,"UTF-8").replaceAll(" ", "%20") + "");
//            httpget.setHeader("Content-type", "text/html;charset=UTF-8");
//            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            org.apache.http.HttpResponse response = client.execute(httpget);

            // response.setHeader("Content-type", "text/html;charset=UTF-8");
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 打印响应状态
            //System.out.println(response.getStatusLine());
            if (entity != null) {
                // 打印响应内容
               result =  EntityUtils.toString(entity, "UTF-8");
//                System.out.printf(EntityUtils.toString(entity, "UTF-8"));
//                JSONObject json = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
//                result = json.getString("result");

            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    }

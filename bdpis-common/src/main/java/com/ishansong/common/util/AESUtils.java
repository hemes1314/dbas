package com.ishansong.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



/**
 * Created by yangguoliang on 2017/6/22.
 *
 * 加密解密工具类
 *
 */
public class AESUtils {
    private static BASE64Encoder base64Encoder = new BASE64Encoder();
    private static BASE64Decoder base64Decoder = new BASE64Decoder();
    private static IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
    private static String CipherType = "AES/CBC/PKCS5Padding"; //"算法/模式/补码方式
    public static String cKey="iss*%Ka3kh5%$6fa";//对方也用此密钥
    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
//        判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(CipherType);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        String  base64Str =base64Encoder.encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。

        return java.net.URLEncoder.encode(base64Str,"utf-8");//此处使用URLEncoder 进行编码，编码之后的数据可以当作参数传递
    }

    // 解密
    public static String Decrypt(String sSrc ) throws Exception {
        try {
            // 判断Key是否正确
            if (cKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (cKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = cKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(CipherType);

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

           String sSrcRest =  java.net.URLDecoder.decode(sSrc,   "utf-8"); //先使用URLEncoder 进行解码
            byte[] encrypted1 = base64Decoder.decodeBuffer(sSrcRest);//再用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
     public static void main(String[] args){

//         String str="FV4F+j6avTlC6+iFeNPr9uo7S8s6G8rkX5kI//W74V4=";

         try {
             System.out.println(URLDecoder.decode("啊啊啊","utf-8"));
             String str=Encrypt(URLDecoder.decode("摩托车，汽车，自行车 都可以"),cKey);
             System.out.println("加密后"+str);
             String str1=Decrypt(str);
             System.out.println("解密后"+str1);
             System.out.println(str.length());

         } catch (Exception e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
         }
     }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            urlNameString = urlNameString.replaceAll("\r", "");
            urlNameString = urlNameString.replaceAll("\n", "");
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 使用StringTokenizer类将字符串按分隔符转换成字符数组
     *
     * @param string       字符串
     * @param divisionChar 分隔符
     * @return 字符串数组
     * @see [类、类#方法、类#成员]
     */
    private static String[] stringAnalytical(String string, String divisionChar) {
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);

        String[] str = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreTokens()) {
            str[i] = new String();
            str[i] = tokenizer.nextToken();
            i++;
        }

        return str;
    }

    private static String[] stringAnalyticalIndex(String src, String divisionChar) {
        String[] str = new String[2];
        int index = src.indexOf(divisionChar);
        str[0] = src.substring(0, index);
        str[1] = src.substring(index + 1, src.length());
        return str;
    }
}
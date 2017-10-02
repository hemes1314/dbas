package test;

import com.ishansong.common.util.WordSegmenterUtils;
import com.ishansong.model.dictionaries.CdCodeWordseg;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by iss on 2017/9/21 下午5:07.
 * <p>
 * 物品备注信息，分词排序
 */
public class GoodsWordTest {


    public static Map<String,Map> dictionaryInfoMap = new HashMap<String, Map>(); //保存数据字典信息


    public static void main(String [] args){

        Map<String,String> mapStr = WordSegmenterUtils.segMore("我要沙发螃蟹");

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
        System.out.println(wordStr);

        //输出分词结果
        String[] wordArray = wordStr.split(" ");

        System.out.println(Arrays.toString(wordArray));



        //将字典信息加载到map中，模拟 从缓存中读取数据的操作
        List<String> strList = new ArrayList<String>();
        try
        {
            BufferedReader bufReader = new BufferedReader(new FileReader("/Users/iss/Desktop/cd_code_wordseg.txt"));
            while(true)
            {
                String lineStr = bufReader.readLine();
                if(lineStr != null) {
                    strList.add(lineStr);
                }else{
                    break;
                }
            }
            bufReader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Map<String,CdCodeWordseg> cdCodeWordsegMap = new HashMap<String,CdCodeWordseg>();

        //输出读取的结果
        for (int h = 0; h < strList.size(); h++) {
            System.out.println(strList.get(h));

            String[] result = strList.get(h).split(",");
            CdCodeWordseg cdCodeWordseg = new CdCodeWordseg();
            cdCodeWordseg.setWord_seg(result[0]);
            cdCodeWordseg.setWord_seg_id(result[1]);
            cdCodeWordseg.setWord_subtype_id(result[2]);
            cdCodeWordseg.setWord_subtype_name(result[3]);
            cdCodeWordseg.setWord_big_subtype_id(result[4]);
            cdCodeWordseg.setCoefficient(result[5]);

            cdCodeWordsegMap.put(result[0],cdCodeWordseg);

        }
        dictionaryInfoMap.put("cdCodeWordsegList",cdCodeWordsegMap);

        //逻辑开始 =======
        //获取缓存的数据字典数据
        Map<String,CdCodeWordseg> cdCodeWordsegResultList =  dictionaryInfoMap.get("cdCodeWordsegList");

        Map<Integer,String> resultMap = new HashMap<Integer,String>();

        //根据物品备注分词之后的结果，将对应的字典信息筛选
        for (int j =0;j<wordArray.length;j++){
            String message = wordArray[j];
            if(message.length() > 1){
                if(cdCodeWordsegResultList.containsKey(message)){
                    CdCodeWordseg res =  cdCodeWordsegResultList.get(message);
                    System.out.println(res.getCoefficient()+"==="+res.getWord_seg()+"==="+res.getWord_subtype_name());
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
            System.out.println("最终的分类:"+resultMap.get(obj[obj.length-1]));

        }else{
            System.out.println("该物品分类标示为其他");
        }

    }



}



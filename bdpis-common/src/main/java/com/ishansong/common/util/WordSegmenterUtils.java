package com.ishansong.common.util;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iss on 2017/9/22 下午3:05.
 * <p>
 * word 分词工具类
 */
public class WordSegmenterUtils {


    public  static Map<String, String> segMore(String text) {
        Map<String, String> map = new HashMap<>();
        for(SegmentationAlgorithm segmentationAlgorithm : SegmentationAlgorithm.values()){
            map.put(segmentationAlgorithm.getDes(), seg(text, segmentationAlgorithm));
        }
        return map;
    }
    private static String seg(String text, SegmentationAlgorithm segmentationAlgorithm) {
        StringBuilder result = new StringBuilder();
        for(Word word : WordSegmenter.segWithStopWords(text, segmentationAlgorithm)){
            result.append(word.getText()).append(" ");
        }
        return result.toString();
    }

}

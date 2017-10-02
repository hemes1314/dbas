package test;

import com.ishansong.model.goods.GoodsTypePrefer;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class GoodsTypePreferTest {

    public static void main(String[] args) {

        String typePrefers = "蛋糕类:0.33,鲜花类:0.11,大件品类:0.11";

        // 取前三条数据
        String[] typePreferArr = typePrefers.split(",");
        if(typePreferArr.length > 3) {
            typePreferArr = Arrays.copyOf(typePreferArr, 3);
        }
        System.out.println(Arrays.asList(typePreferArr));

        Map<String, String> coefficientMap = new HashMap<>();
        coefficientMap.put("大件品类", "12");
        coefficientMap.put("鲜花类", "7");

        // 组装成对象
        List<GoodsTypePrefer> result = new ArrayList<>();
        // 获取字典数据
        for(String typePreferStr : typePreferArr) {
            GoodsTypePrefer goodsTypePrefer = new GoodsTypePrefer();
            String[] typePreferStrSplit = typePreferStr.split(":");
            goodsTypePrefer.setGoodsType(typePreferStrSplit[0]);
            goodsTypePrefer.setPrefer(typePreferStrSplit[1]);
            // 获取优先级
            goodsTypePrefer.setCoefficient(coefficientMap.get(goodsTypePrefer.getGoodsType()));
            result.add(goodsTypePrefer);
        }
        Collections.sort(result);
        System.out.println(result);

//        System.out.println(Collections.EMPTY_LIST.add("as"));
    }
}


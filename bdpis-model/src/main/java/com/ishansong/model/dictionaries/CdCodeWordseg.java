package com.ishansong.model.dictionaries;

/**
 * Created by iss on 2017/9/20 下午8:01.
 * <p>
 * 订单物品画像-物品分类（实体类）
 */
public class CdCodeWordseg {

    private String word_seg;
    private String word_seg_id;
    private String word_subtype_id;
    private String word_subtype_name;
    private String word_big_subtype_id;
    private String word_big_subtype_name;
    private String coefficient;

    public String getWord_seg() {
        return word_seg;
    }

    public void setWord_seg(String word_seg) {
        this.word_seg = word_seg;
    }

    public String getWord_seg_id() {
        return word_seg_id;
    }

    public void setWord_seg_id(String word_seg_id) {
        this.word_seg_id = word_seg_id;
    }

    public String getWord_subtype_id() {
        return word_subtype_id;
    }

    public void setWord_subtype_id(String word_subtype_id) {
        this.word_subtype_id = word_subtype_id;
    }

    public String getWord_subtype_name() {
        return word_subtype_name;
    }

    public void setWord_subtype_name(String word_subtype_name) {
        this.word_subtype_name = word_subtype_name;
    }

    public String getWord_big_subtype_id() {
        return word_big_subtype_id;
    }

    public void setWord_big_subtype_id(String word_big_subtype_id) {
        this.word_big_subtype_id = word_big_subtype_id;
    }

    public String getWord_big_subtype_name() {
        return word_big_subtype_name;
    }

    public void setWord_big_subtype_name(String word_big_subtype_name) {
        this.word_big_subtype_name = word_big_subtype_name;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }
}

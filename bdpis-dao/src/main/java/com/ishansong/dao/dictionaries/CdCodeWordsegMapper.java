package com.ishansong.dao.dictionaries;

import com.ishansong.model.dictionaries.CdCodeWordseg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iss on 2017/9/21 上午11:56.
 * <p>
 * (描述)
 */
@Repository
public interface CdCodeWordsegMapper {

    public List<CdCodeWordseg> queryByAll();


}

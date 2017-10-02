package com.ishansong.serviceimpl.dictionaries;

import com.ishansong.dao.dictionaries.CdCodeWordsegMapper;
import com.ishansong.model.dictionaries.CdCodeWordseg;
import com.ishansong.service.dictionaries.CdCodeWordsegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iss on 2017/9/21 下午2:07.
 * <p>
 *  订单物品画像-物品分类 服务类
 */
@Service("cdCodeWordsegService")
public class CdCodeWordsegServiceImpl implements CdCodeWordsegService {


    @Autowired
    private CdCodeWordsegMapper cdCodeWordsegMapper;

    @Override
    public List<CdCodeWordseg> queryByAll() {
        return cdCodeWordsegMapper.queryByAll();
    }
}

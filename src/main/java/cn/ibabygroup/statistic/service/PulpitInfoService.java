package cn.ibabygroup.statistic.service;

import cn.ibabygroup.statistic.dao.PulpitInfoDao;
import cn.ibabygroup.statistic.model.PulpitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 11:40
 */
@Service
public class PulpitInfoService {
    @Autowired
    private PulpitInfoDao dao;

    public PulpitInfo getPulpitInfoById(String id) {
        return dao.findOne(id);
    }
}

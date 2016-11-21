package cn.ibabygroup.statistic.dao;

import cn.ibabygroup.statistic.model.PulpitInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 11:38
 */

@Component
public interface PulpitInfoDao extends MongoRepository<PulpitInfo, String> {
}

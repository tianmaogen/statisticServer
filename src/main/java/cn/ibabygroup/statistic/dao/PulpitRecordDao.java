package cn.ibabygroup.statistic.dao;

import cn.ibabygroup.statistic.model.PulpitRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 14:24
 */

public interface PulpitRecordDao extends MongoRepository<PulpitRecord, String> {
}

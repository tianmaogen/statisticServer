package cn.ibabygroup.test;

import cn.ibabygroup.statistic.Bootstrap;
import cn.ibabygroup.statistic.model.PulpitInfo;
import cn.ibabygroup.statistic.service.PageDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 15:59
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class testMongotemplate {


    @Test
    public void testAutowired() {
        Criteria criteria = Criteria.where("pulpitName").is("[产科] 高龄孕妇妊娠相关问题");
        System.out.println(PageDataService.getModelsWithCriteria(criteria, PulpitInfo.class));
    }
}

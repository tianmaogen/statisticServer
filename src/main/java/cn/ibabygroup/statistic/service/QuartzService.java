package cn.ibabygroup.statistic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 14:31
 */
@Component
@EnableScheduling
@Slf4j
public class QuartzService {

    @Autowired
    private PulpitRecordService pulpitRecordService;

    private static SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

//    @Scheduled(cron = "0/30 * 18-23 * * ?")//每晚18-23点，每半分钟一次
    @Scheduled(cron = "0/30 * * * * *")//每半分钟一次
    public void minute() {
//        pulpitRecordService.test();
        pulpitRecordService.recordPulpitInfo();
        pulpitRecordService.updatePulpitCount();
//        System.out.println(format.format(new Date()));
    }

}

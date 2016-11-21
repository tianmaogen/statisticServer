package cn.ibabygroup.statistic.config;

import cn.ibabygroup.statistic.service.PageDataService;
import org.dozer.DozerBeanMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 16:16
 */
@Configuration
public class DozerConfig {
    @Bean
    @ConditionalOnMissingBean
    public DozerBeanMapper dozerBeanMapper() {
        List<String> mappingFiles = Collections.singletonList(
                "dozer/CommonsMapper.xml"
        );
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(mappingFiles);
        PageDataService.setDozerBeanMapper(dozerBeanMapper);
        return dozerBeanMapper;
    }


}

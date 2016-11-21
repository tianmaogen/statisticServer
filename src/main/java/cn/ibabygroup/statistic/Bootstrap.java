package cn.ibabygroup.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 10:59
 */
@SpringBootApplication
@EnableSwagger2
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}

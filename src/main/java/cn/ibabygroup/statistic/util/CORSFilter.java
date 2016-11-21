package cn.ibabygroup.statistic.util;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支持跨域调用，仅作测试使用
 * Created by Crowhyc on 2016/4/18 for ibaby-ibabygroup
 */

@Component
@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")
public class CORSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("--------------进入过滤器----------------------------");
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type,Token");
            response.addHeader("Access-Control-Max-Age", "1");
        }
        filterChain.doFilter(request, response);
    }

    public CORSFilter() {
        System.out.println("filter实例化");
    }
}

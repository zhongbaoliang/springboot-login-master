package com.alibaba.filter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**过滤器
 * 是servlet中最实用的技术之一
 * 可以用来过滤掉不符合要求的请求
 * 通常用作session校验，判断用户权限
 * 如果不符合条件，则会被拦截到特殊的地址或者基于特殊的响应
 *
 * 是JavaEE的标准，依赖于servlet，生命周期也与容器一致
 * 因此可以其在注销时释放资源或者数据入库
 * 基于回调函数实现，无法注入成为IOC容器的bean
 *
 * 配置方式：
 * 1. @WebFilter
 * 2. @Bean
 *
 * init 方法：在容器中创建当前过滤器的时候自动调用
 * destroy 方法：在容器中销毁当前过滤器的时候自动调用
 * doFilter 方法：过滤的具体操作
 *
 * 总结：过滤器属于JavaEE，基于回调函数实现
 * 回调函数通过接口来实现
 */

@Log4j2
//@WebFilter(filterName = "MyFilter",urlPatterns = {"/*"})
public class MyFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化过滤器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        String requestUri = request.getRequestURI();
        log.info("请求的地址是 "+requestUri);
        Object user = request.getSession().getAttribute("user");
        //System.out.println("filter getSession(): " + request.getSession().getId());
        if (user != null ||requestUri.contains("login"))
            filterChain.doFilter(servletRequest,servletResponse);//转交给下一个过滤器
        else{
            wrapper.sendRedirect("/login.html");
        }

    }

    @Override
    public void destroy() {
        //在服务器关闭时销毁
        log.info("销毁过滤器");

    }
}
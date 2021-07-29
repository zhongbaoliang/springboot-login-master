package com.alibaba.interceptor;

import com.alibaba.bean.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**拦截器
 *登录状态认证
 * 记录系统日志
 * 通用处理（AOP？)
 *
 *
 * 拦截器是SpringMVC的内容，依赖于web框架，通常用于用户权限验证或者记录日志
 * 拦截器是基于反射来实现，因此拦截器可以注入到IOC容器中
 *
 *
 *
 * 总结：拦截器属于SpringMVC，基于反射，主要用于权限验证和记录日志
 *
 *
 */

@Log4j2
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**在 Controller 处理请求之前被调用
         * 返回值是 boolean类型， 如果是true就进行下一步操作；
         * 若返回false，则证明不符合拦截条件，在失败的时候不会包含任何响应，
         * 此时需要调用对应的response返回对应响应。
         *
         */
        log.info("【MyInterceptor】调用了:{}", request.getRequestURI());
        request.setAttribute("requestTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        /**postHandler：在 Controller 处理请求执行完成后、生成视图前执行，
         * 可以通过ModelAndView对视图进行处理，当然ModelAndView也可以设置为 null
         *
         */
        if (!request.getRequestURI().contains("/login")||!request.getRequestURI().contains("/regist")) {
            HttpSession session = request.getSession();
            //System.out.println("Interceptor getSession(): " + request.getSession().getId());
            User user=(User) session.getAttribute("user");
            //System.out.println(user.getUsername());
            //String sessionName = (String) session.getAttribute("username");
            //if ("username".equals(sessionName)) {
               // log.info("【MyInterceptor】当前浏览器存在 session:{}",sessionName);
            //}
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        /**afterCompletion：在 DispatcherServlet 完全处理请求后被调用，
         * 通常用于记录消耗时间，也可以对一些资源进行处理。
         *
         */
        long duration = (System.currentTimeMillis() - (Long)request.getAttribute("requestTime"));
        log.info("【MyInterceptor】[{}]调用耗时:{}ms",request.getRequestURI(), duration);

    }
}

package com.alibaba.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**监听器通常用于监听 Web 应用程序中对象的创建、销毁等动作的发送，同时对监听的情况作出相应的处理，
 * 最常用于统计网站的在线人数、访问量等。
 *ServletContextListener：用来监听 ServletContext 属性的操作，比如新增、修改、删除。
 * HttpSessionListener：用来监听 Web 应用中的 Session 对象，通常用于统计在线情况。
 * ServletRequestListener：用来监听 Request 对象的属性操作。
 *
 * 总结：监听器属于JavaEE，基于回调函数实现，主要用于统计在线情况、周期性任务
 */
@Log4j2
public class MyHttpSessionListener implements HttpSessionListener {

    public static AtomicInteger userCount = new AtomicInteger(0);

    @Override
    public synchronized void sessionCreated(HttpSessionEvent se) {
        userCount.getAndIncrement();


        se.getSession().getServletContext().setAttribute("username", userCount.get());
        System.out.println("sessionListener getSession(): " + se.getSession().getId());
        log.info("【在线人数】人数增加为:{}",userCount.get());


        //此处可以在ServletContext域对象中为访问量计数，然后传入过滤器的销毁方法
        //在销毁方法中调用数据库入库，因为过滤器生命周期与容器一致
    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent se) {
        userCount.getAndDecrement();
        se.getSession().getServletContext().setAttribute("sessionCount", userCount.get());
        System.out.println("sessionListener getSession(): " + se.getSession().getId());
        log.info("【在线人数】人数减少为:{}",userCount.get());
    }
}

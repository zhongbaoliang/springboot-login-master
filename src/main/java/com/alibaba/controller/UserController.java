package com.alibaba.controller;

import com.alibaba.bean.Result;
import com.alibaba.bean.User;
import com.alibaba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//@RestController=@Controller+@ResponseBody
//@Controller=@Component+@Scope
@Controller
//@Component
//@Scope("prototype")
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/regist")
    public Result regist(User user){
        return userService.regist(user);
    }

    /**
     * 登录
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(User user, HttpServletRequest servletRequest){

        Result login = userService.login(user);
        if(login.isSuccess()){
            //System.out.println("controller getSession "+servletRequest.getSession().getId());
            servletRequest.getSession().setAttribute("",user.getUsername());

        }
        return login;
    }

}


package com.ibuyi.ssm.controller;

import com.alibaba.fastjson.JSONObject;
import com.ibuyi.ssm.bean.UserLogin;
import com.ibuyi.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//将 @RequestMapping 注解在 UserController 类上，这时类的注解是相对于 Web 根目录，而方法上的是相对于类上的路径
//  后台服务接口路径  /user
@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //实现用户登录功能
    @ResponseBody
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public String userLogin( @RequestParam("userAccount") String userAccount , @RequestParam("userPwd") String userPwd) {

        UserLogin userLogin = userService.userLogin(userAccount, userPwd);
        JSONObject json = new JSONObject();

        if (userLogin != null) {
            //如果用户不为空
            //这里应该返回的是json数据,登录成功与否
            json.put("res", "success");
            json.put("username", userAccount);
            json.put("msg", "登录成功");
        } else {
            //如果为空
            json.put("res", "fail");
            json.put("username", null);
            json.put("msg", "登录失败");
        }
        return json.toJSONString();
    }

    //实现用户注册功能
    @ResponseBody
    @RequestMapping(value = "/register", produces = {"application/json;charset=UTF-8"})
    public String userRegister(@RequestParam("userAccount") String userAccount, @RequestParam("userPwd") String userPwd ,
                               @RequestParam("userName") String userName , @RequestParam("userGender") String userGender,
                               @RequestParam("userEmail") String userEmail ,
                               @RequestParam("userPhoneNumber") String userPhoneNumber,
                               @RequestParam("userCardNumber") String userCardNumber)
    {
        UserLogin userLogin = userService.findUserAll(userAccount);
        JSONObject json = new JSONObject();
        if (userLogin == null) {
            int i = userService.userRegister(userAccount, userPwd,userName,userGender,userEmail,userPhoneNumber,userCardNumber);

            //注册逻辑，判断用户是否为空不为空才能注册
            if (i == 1  ) {
                //如果用户不为空
                //这里应该返回的是json数据,注册成功与否
                json.put("res", "success");
                json.put("username", userAccount);
                json.put("gender", "男");
                json.put("userEmail",null);
                json.put("userPhoneNumber",null);
                json.put("userCardNumber", null);
                json.put("msg", "注册成功");
            } else {
                //如果为空
                json.put("res", "fail");
                json.put("username", null);
                json.put("gender", null);
                json.put("userEmail", null);
                json.put("userPhoneNumber", null);
                json.put("userCardNumber", null);
                json.put("msg", "注册失败");
            }
        } else {
            json.put("res", "fail");
            json.put("username", null);
            json.put("gender", null);
            json.put("userEmail", null);
            json.put("userPhoneNumber", null);
            json.put("userCardNumber", null);
            json.put("msg", "账号已注册");
        }
        return json.toJSONString();
    }

    //查询数据库里的数据
    @ResponseBody
    @RequestMapping(value = "/findAll", produces = {"application/json;charset=UTF-8"})
    public String findUserAll(@RequestParam("userAccount") String useraccount) {
         UserLogin userLogin = userService.findUserAll(useraccount);
         JSONObject json = new JSONObject();
         if(userLogin!=null){
             json.put("result","success");
             json.put("user",userLogin);
         }else{
             json.put("result","fail");
             json.put("user",null);
         }
         return json.toJSONString();
    }

    //更新数据库里的银行卡号
    @ResponseBody
    @RequestMapping(value = "/updatecard", produces = {"application/json;charset=UTF-8"})
    public String updateCard(@RequestParam("card") String card,@RequestParam("userName") String usernmae) {
        int result = userService.updateCard(card, usernmae);
        JSONObject json = new JSONObject();
        if(result>0){
            json.put("result","success");
        }else{
            json.put("result","fail");
        }
        return json.toJSONString();
    }

    //更新数据库里的用户昵称
    @ResponseBody
    @RequestMapping(value = "/updateName", produces = {"application/json;charset=UTF-8"})
    public String updateName(@RequestParam("userName") String username,
                             @RequestParam("userAccount") String useraccount) {
        int result = userService.updateName(username, useraccount);
        JSONObject json = new JSONObject();
        if(result>0){
            json.put("result","success");
        }else{
            json.put("result","fail");
        }
        return json.toJSONString();
    }

    //更新数据库里的用户邮箱
    @ResponseBody
    @RequestMapping(value = "/updateEmail", produces = {"application/json;charset=UTF-8"})
    public String updateEmail(@RequestParam("updateEmail") String updateemail,
                             @RequestParam("userAccount") String useraccount) {
        int result = userService.updateEmail(updateemail, useraccount);
        JSONObject json = new JSONObject();
        if(result>0){
            json.put("result","success");
        }else{
            json.put("result","fail");
        }
        return json.toJSONString();
    }

    //更新数据库里的用户电话号码
    @ResponseBody
    @RequestMapping(value = "/updatePhoneNumber", produces = {"application/json;charset=UTF-8"})
    public String updatePhoneNumber(@RequestParam("updateTelphone") String updatetelphone,
                              @RequestParam("userAccount") String useraccount) {
        int result = userService.updatePhoneNumber(updatetelphone, useraccount);
        JSONObject json = new JSONObject();
        if(result>0){
            json.put("result","success");
        }else{
            json.put("result","fail");
        }
        return json.toJSONString();
    }
}

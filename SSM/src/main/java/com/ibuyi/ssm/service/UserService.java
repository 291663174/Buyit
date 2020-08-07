package com.ibuyi.ssm.service;

import com.ibuyi.ssm.bean.UserLogin;

public interface UserService {
    //根据用户账号密码去数据库查询，判断是否能登录
    UserLogin userLogin(String userAccount,String userPwd);

    //将未注册的用户账号相关信息插入到数据库中
    int userRegister(String userAccount,String userPwd,String userName,
                     String userGender,String userEmail,String userPhoneNumber,String userCardNumber);

    //根据用户账号去数据库查找数据
    UserLogin findUserAll(String userAccount);

    //根据用户昵称去数据库更新银行卡号
    int updateCard(String card,String username);

    //根据用户账号去数据库更新用户昵称
    int updateName(String username,String useraccount);

    //根据用户账号去数据库更新用户电话号码
    int updatePhoneNumber(String userphonenumber,String useraccount);

    //根据用户账号去数据库更新用户电子邮箱
    int updateEmail(String useremail,String useraccount);
}

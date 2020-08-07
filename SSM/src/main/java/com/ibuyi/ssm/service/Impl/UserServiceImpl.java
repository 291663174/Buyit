package com.ibuyi.ssm.service.Impl;

import com.ibuyi.ssm.bean.UserLogin;
import com.ibuyi.ssm.dao.UserLoginDAO;
import com.ibuyi.ssm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//告诉Spring当Spring要创建UserServiceImpl的的实例时，bean的名字必须叫做"userService"，
// 这样当Action需要使用UserServiceImpl的的实例时,就可以由Spring创建好的"userService"，
// 然后注入给Action。在Action只需要声明一个名字叫“userService”的变量来接收由Spring注入
// 的"userService"即可.Action声明的“userService”变量的类型必须是“UserServiceImpl”
// 或者是其父类“UserService”，否则由于类型不一致而无法注入
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserLoginDAO userLoginDAO;
    private String userName;
    private String userEmail;

    @Override
    public UserLogin userLogin(String userAccount, String userPwd) {
        return userLoginDAO.userLogin(userAccount,userPwd);
    }

    @Override
    public int userRegister(String userAccount, String userPwd,String userName,String userGender,
                            String userEmail,String userPhoneNumber,String userCardNumber) {
        return userLoginDAO.userRegister(userAccount,userPwd,userName,userGender,userEmail,userPhoneNumber,
                            userCardNumber);
    }

    @Override
    public UserLogin findUserAll(String userAccount) {
        return userLoginDAO.findUserAll(userAccount);
    }

    @Override
    public int updateCard(String card, String username) {
        return userLoginDAO.updateCard(card, username);
    }

   @Override
    public int updateName(String username, String useraccount) {
        return userLoginDAO.updateName(username, useraccount);
    }

    @Override
    public int updatePhoneNumber(String userphonenumber, String useraccount) {
        return userLoginDAO.updatePhoneNumber(userphonenumber, useraccount);
    }

    @Override
    public int updateEmail(String useremail, String username) {
        return userLoginDAO.updateEmail(useremail, username);
    }
}

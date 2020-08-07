package com.ibuyi.ssm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

//使用@Data 不用手动添加get set方法，但是如果项目中其他类中使用getset方法，如果报错，原因是idea中没有添加Lombok插件，添加上插件便可以解决
@Data
//会生成一个包含所有变量，同时如果变量使用了NotNull annotation ， 会进行是否为空的校验，全部参数的构造函数的自动生成，该注解的作用域也是只有在实体类上，参数的顺序与属性定义的顺序一致。
@AllArgsConstructor
//无参构造函数
@NoArgsConstructor

public class UserLogin {
    //对应数据库的 7 列名称
    private long userId;
    private String userAccount;
    private String userPwd;
    private String userGender;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userCardNumber;
}

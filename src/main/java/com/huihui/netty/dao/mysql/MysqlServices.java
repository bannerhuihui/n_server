package com.huihui.netty.dao.mysql;

import com.huihui.netty.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlServices.class);

    @Autowired
    private JdbcTemplate template;

    public UserPojo queryUserById(int userId){
        if(userId != 0){
            UserPojo userPojo = null;
            String sql = "SELECT u.userId,u.userName,u.loginMobile,u.imageUrl,s.shopId,s.shopName  FROM u_user u LEFT JOIN u_org_user o ON o.userId = u.userId LEFT JOIN b_shop s ON s.orgId = o.orgId WHERE u.userId = "+userId;
            List<UserPojo> query = template.query(sql, new BeanPropertyRowMapper<>(UserPojo.class));
            if(query!= null && query.size()>0){
                userPojo = query.get(0);
            }
            return  userPojo;
        }
        return null;
    }

}

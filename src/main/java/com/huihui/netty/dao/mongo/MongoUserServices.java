package com.huihui.netty.dao.mongo;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.MongoClicationName;
import com.huihui.netty.common.StateConfig;
import com.huihui.netty.pojo.UserPojo;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MongoUserServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoUserServices.class);

    @Autowired
    private MongoTemplate template;

    /**
     * 保存用户的登录信息
     * @param userPojo
     * @return
     */
    public boolean saveUser(UserPojo userPojo){
        LOGGER.info("MongoUserServices#saveUser保存用户的登录信息，入参："+ JSONObject.toJSONString(userPojo));
        if(userPojo!= null){
            try {
                template.save(userPojo, MongoClicationName.USER_LOGIN.getName());
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 根据用户id查询用户的登录信息，查看是否登录过
     * @param userId
     * @return
     */
    public UserPojo queryUserByUserId(int userId){
        LOGGER.info("MongoUserServices#queryUserByUserId查询用户的登录信息，入参："+ userId);
        if(userId != 0){
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("state").is(StateConfig.TRUE_STATE.getCode());
            criteria.and("userId").is(userId);
            query.addCriteria(criteria);
            UserPojo userPojo = template.findOne(query, UserPojo.class, MongoClicationName.USER_LOGIN.getName());
            LOGGER.info("MongoUserServices#queryUserByUserId查询用户的登录信息，出参："+ JSONObject.toJSONString(userPojo));
            return userPojo;
        }
        return null;
    }


    /**
     * 更新用户的状态
     * @param userPojo
     * @return
     */
    public boolean updateUserByUserId(UserPojo userPojo){
        LOGGER.info("MongoUserServices#updateUserByUserId更新用户登录状态入参："+ JSONObject.toJSONString(userPojo));
        if(userPojo.getUserId() == 0){
            return false;
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("userId").is(userPojo.getUserId());
        criteria.and("state").is(StateConfig.TRUE_STATE.getCode());
        query.addCriteria(criteria);
        Update update = new Update();
        if(userPojo.getLastUpdTime() != 0){
            update.set("lastUpdTime",userPojo.getLastUpdTime());
        }
        if(StringUtils.isNotEmpty(userPojo.getUserName())){
            update.set("userName",userPojo.getUserName());
        }
        if(StringUtils.isNotEmpty(userPojo.getShopName())){
            update.set("shopName",userPojo.getShopName());
        }
        if(userPojo.getShopId() != 0){
            update.set("shopId",userPojo.getShopId());
        }
        if(userPojo.getAttachments() != null){
            update.set("attachments",userPojo.getAttachments());
        }
        if(userPojo.getState() != 0){
            update.set("state",userPojo.getState());
        }
        WriteResult updUser = template.updateMulti(query, update, UserPojo.class, MongoClicationName.USER_LOGIN.getName());
        LOGGER.info("MongoUserServices#updateUserByUserId更新用户登录状态，修改结果："+ (updUser.isUpdateOfExisting() ? "成功！":"失败！"));
        return updUser.isUpdateOfExisting();
    }


}

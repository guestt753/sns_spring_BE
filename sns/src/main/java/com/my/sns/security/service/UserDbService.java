package com.my.sns.security.service;

import java.util.List;

import com.my.sns.security.entity.UserEntity;
import com.my.sns.security.entity.UserRoleEntity;



public interface UserDbService {
    public UserEntity getUser(String loginUserId);
    public List<UserRoleEntity> getUserRoles(String loginUserId);
}

package com.my.sns.security.service;

import java.util.List;

import com.my.sns.security.entity.UserEntity;
import com.my.sns.security.entity.UserRoleEntity;



public interface UserDbService {
    public UserEntity getUser(String loginUserId);
    public UserEntity getUser(Long userNo);
    public List<UserRoleEntity> getUserRoles(String loginUserId);
}

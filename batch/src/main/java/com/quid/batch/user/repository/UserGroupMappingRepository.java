package com.quid.batch.user.repository;


import com.quid.batch.user.entity.UserGroupMappingEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Integer> {

    List<UserGroupMappingEntity> findByUserGroupId(String userGroupId);
}
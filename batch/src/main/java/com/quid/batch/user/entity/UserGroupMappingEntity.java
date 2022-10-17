package com.quid.batch.user.entity;

import com.quid.batch.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "user_group_mapping")
@NoArgsConstructor
@IdClass(UserGroupMappingId.class)
public class UserGroupMappingEntity extends BaseEntity {
    @Id
    private String userGroupId;
    @Id
    private String userId;

    private String userGroupName;
    private String description;

    @Builder
    public UserGroupMappingEntity(String userGroupId, String userId, String userGroupName,
        String description) {
        this.userGroupId = userGroupId;
        this.userId = userId;
        this.userGroupName = userGroupName;
        this.description = description;
    }
}
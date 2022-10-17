package com.quid.batch.user.entity;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserGroupMappingId implements Serializable {

    private String userGroupId;
    private String userId;

    @Builder
    public UserGroupMappingId(String userGroupId, String userId) {
        this.userGroupId = userGroupId;
        this.userId = userId;
    }
}
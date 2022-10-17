package com.quid.batch.user.entity;

import com.quid.batch.base.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Getter
@ToString
@Entity
@Table(name = "user")
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class User extends BaseEntity {

    @Id
    private String userId;

    private String userName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String phone;

    @Type(type = "json")
    private Map<String, Object> meta;

    public String getUuid() {
        String uuid = null;
        if (meta.containsKey("uuid")) {
            uuid = String.valueOf(meta.get("uuid"));
        }
        return uuid;

    }

    @Builder
    public User(String userName, UserStatus status, String phone, Map<String, Object> meta) {
        this.userName = userName;
        this.status = status;
        this.phone = phone;
        this.meta = meta;
    }
}
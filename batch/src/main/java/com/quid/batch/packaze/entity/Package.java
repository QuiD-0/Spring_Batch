package com.quid.batch.packaze.entity;

import com.quid.batch.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "package")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Package extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageSeq;

    private String packageName;

    private Integer count;

    private Integer period;

    @Builder
    public Package(String packageName, Integer count, Integer period) {
        this.packageName = packageName;
        this.count = count;
        this.period = period;
    }
}

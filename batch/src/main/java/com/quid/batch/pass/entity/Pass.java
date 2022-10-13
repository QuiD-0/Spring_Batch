package com.quid.batch.pass.entity;

import com.quid.batch.base.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "pass")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pass extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passSeq;
    private Integer packageSeq;
    private String userId;

    @Enumerated(EnumType.STRING)
    private PassStatus status;
    private Integer remainingCount;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

    @Builder
    public Pass(Integer packageSeq, String userId, PassStatus status, Integer remainingCount,
        LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime expiredAt) {
        this.packageSeq = packageSeq;
        this.userId = userId;
        this.status = status;
        this.remainingCount = remainingCount;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.expiredAt = expiredAt;
    }

    public Pass expire() {
        this.status = PassStatus.EXPIRED;
        this.expiredAt = LocalDateTime.now();
        return this;
    }
}
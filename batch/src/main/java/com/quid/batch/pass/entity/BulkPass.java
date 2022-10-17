package com.quid.batch.pass.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "bulk_pass")
@NoArgsConstructor
public class BulkPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bulkPassSeq;

    private Integer packageSeq;

    private String userGroupId;

    @Enumerated(EnumType.STRING)
    private BulkPassStatus bulkPassStatus;

    private Integer count;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public BulkPass setComplete() {
        this.bulkPassStatus = BulkPassStatus.COMPLETED;
        return this;
    }

    @Builder
    public BulkPass(Integer packageSeq, String userGroupId, BulkPassStatus bulkPassStatus,
        Integer count, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.packageSeq = packageSeq;
        this.userGroupId = userGroupId;
        this.bulkPassStatus = bulkPassStatus;
        this.count = count;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public Pass toPass(BulkPass bulkPass, String userId){
        return Pass.builder()
            .userId(userId)
            .remainingCount(bulkPass.count)
            .endedAt(bulkPass.endedAt)
            .status(PassStatus.READY)
            .startedAt(bulkPass.startedAt)
            .packageSeq(bulkPass.packageSeq)
            .build();
    }
}

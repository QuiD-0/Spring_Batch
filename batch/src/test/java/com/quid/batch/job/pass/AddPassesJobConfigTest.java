package com.quid.batch.job.pass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.quid.batch.pass.BulkPassRepository;
import com.quid.batch.pass.PassRepository;
import com.quid.batch.pass.entity.BulkPass;
import com.quid.batch.pass.entity.BulkPassStatus;
import com.quid.batch.pass.entity.Pass;
import com.quid.batch.pass.entity.PassStatus;
import com.quid.batch.user.entity.UserGroupMappingEntity;
import com.quid.batch.user.repository.UserGroupMappingRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

@ExtendWith(MockitoExtension.class)
class AddPassesJobConfigTest {

    @Mock
    private StepContribution stepContribution;

    @Mock
    private ChunkContext chunkContext;

    @Mock
    private PassRepository passRepository;

    @Mock
    private BulkPassRepository bulkPassRepository;

    @Mock
    private UserGroupMappingRepository userGroupMappingRepository;

    @InjectMocks
    private AddPassTasklet addPassTasklet;

    @Test
    public void test_execute() throws Exception {
        // given
        final String userGroupId = "GROUP";
        final String userId = "A1000000";
        final Integer packageSeq = 1;
        final Integer count = 10;

        final LocalDateTime now = LocalDateTime.now();

        final BulkPass bulkPassEntity = BulkPass.builder()
            .packageSeq(packageSeq)
            .bulkPassStatus(BulkPassStatus.READY)
            .userGroupId(userGroupId)
            .count(count)
            .startedAt(now)
            .endedAt(now.plusDays(60))
            .build();

        final UserGroupMappingEntity userGroupMappingEntity = UserGroupMappingEntity.builder()
            .userGroupId(userGroupId)
            .userId(userId)
            .build();

        // when
        when(bulkPassRepository.findByBulkPassStatusAndStartedAtGreaterThan(eq(BulkPassStatus.READY), any())).thenReturn(
            List.of(bulkPassEntity));
        when(userGroupMappingRepository.findByUserGroupId(eq("GROUP"))).thenReturn(List.of(userGroupMappingEntity));

        RepeatStatus repeatStatus = addPassTasklet.execute(stepContribution, chunkContext);

        // then
        assertEquals(RepeatStatus.FINISHED, repeatStatus);

        // 추가된 PassEntity 값을 확인합니다.
        ArgumentCaptor<List> passEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(passRepository, times(1)).saveAll(passEntitiesCaptor.capture());
        final List<Pass> passEntities = passEntitiesCaptor.getValue();

        assertEquals(1, passEntities.size());
        final Pass passEntity = passEntities.get(0);
        assertEquals(packageSeq, passEntity.getPackageSeq());
        assertEquals(userId, passEntity.getUserId());
        assertEquals(PassStatus.READY, passEntity.getStatus());
        assertEquals(count, passEntity.getRemainingCount());

    }

}
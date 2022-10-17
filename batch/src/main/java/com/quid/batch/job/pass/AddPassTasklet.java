package com.quid.batch.job.pass;

import com.quid.batch.pass.BulkPassRepository;
import com.quid.batch.pass.PassRepository;
import com.quid.batch.pass.entity.BulkPass;
import com.quid.batch.pass.entity.BulkPassStatus;
import com.quid.batch.pass.entity.Pass;
import com.quid.batch.user.entity.UserGroupMappingEntity;
import com.quid.batch.user.repository.UserGroupMappingRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class AddPassTasklet implements Tasklet {

    private final PassRepository passRepository;

    private final BulkPassRepository bulkPassRepository;

    private final UserGroupMappingRepository userGroupMappingRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {

        final LocalDateTime startedAt = LocalDateTime.now().minusDays(1);
        final List<BulkPass> bulkPasses = bulkPassRepository.findByBulkPassStatusAndStartedAtGreaterThan(
            BulkPassStatus.READY, startedAt);

        bulkPasses.stream().forEach((bulkPass) -> {
            bulkPass.setComplete();
            addPasses(bulkPass,
                userGroupMappingRepository.findByUserGroupId(bulkPass.getUserGroupId()).stream()
                    .map(UserGroupMappingEntity::getUserId).toList());
        });

        return RepeatStatus.FINISHED;
    }

    private int addPasses(BulkPass bulkPass, List<String> userIds) {
        List<Pass> passList = userIds.stream().map(
            (userId) -> bulkPass.toPass(bulkPass, userId)).toList();
        return passRepository.saveAll(passList).size();
    }
}

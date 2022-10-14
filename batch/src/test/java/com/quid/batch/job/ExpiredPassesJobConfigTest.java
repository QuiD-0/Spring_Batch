package com.quid.batch.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.quid.batch.config.TestBatchConfig;
import com.quid.batch.pass.PassRepository;
import com.quid.batch.pass.entity.Pass;
import com.quid.batch.pass.entity.PassStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ExpirePassesJobConfig.class, TestBatchConfig.class})
class ExpiredPassesJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PassRepository passRepository;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void expire_passes_step() throws Exception {
        addPassEntities(10);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals("expirePassesJob", jobInstance.getJobName());
    }

    private void addPassEntities(int size) {
        final LocalDateTime now = LocalDateTime.now();

        List<Pass> passEntities = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            Pass passEntity = Pass.builder()
                .packageSeq(1)
                .userId("a" + 100000 + i)
                .status(PassStatus.PROGRESSED)
                .remainingCount(11)
                .startedAt(now.minusDays(60))
                .endedAt(now.minusDays(1))
                .build();
            passEntities.add(passEntity);
        }
        passRepository.saveAll(passEntities);

    }
}
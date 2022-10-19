package com.quid.batch.job.pass;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AddPassesJobConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final AddPassTasklet addPassTasklet;

  @Bean
  public Job addPassesJob() {
    return this.jobBuilderFactory.get("addPassesJob")
        .start(addPassesStep())
        .build();
  }

  @Bean
  public Step addPassesStep() {
    return this.stepBuilderFactory.get("addPassesStep")
        .tasklet(addPassTasklet)
        .build();
  }
}

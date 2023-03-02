package com.bestreads.bookrecommendations.awspersonalize;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;

@Configuration
public class AwsPersonalizeConfiguration {
    @Value("${AWS_REGION}")
    private Region region;

    @Bean
    public PersonalizeRuntimeClient personalizeRuntimeClient() {
        return PersonalizeRuntimeClient.builder().region(region).build();
    }
}

package com.bestreads.bookrecommendations.awspersonalize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalize.PersonalizeClient;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class AwsPersonalizeController {

    private final AwsPersonalizeService awsPersonalizeService;
    private PersonalizeClient personalizeClient;
    private PersonalizeRuntimeClient personalizeRuntimeClient;
    @Value("${AWS_CAMPAIGN_ARN}")
    private String campaignArn;
    @Value("${AWS_REGION}")
    private Region region;

    @PostConstruct
    private void initialiseAmazon() {
        this.personalizeClient = PersonalizeClient.builder()
                .region(region)
                .build();
        this.personalizeRuntimeClient = PersonalizeRuntimeClient.builder()
                .region(region)
                .build();
    }

    @Autowired
    public AwsPersonalizeController (AwsPersonalizeService awsPersonalizeService) {this.awsPersonalizeService = awsPersonalizeService;}

    @GetMapping("/public/book/get-recs")
    public ArrayList<String> getRecs(@Param("isbn") String isbn) {
        return awsPersonalizeService.getRecs(this.personalizeRuntimeClient,campaignArn, isbn);
    }

}

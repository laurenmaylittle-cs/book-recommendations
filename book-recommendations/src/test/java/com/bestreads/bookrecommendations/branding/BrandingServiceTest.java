package com.bestreads.bookrecommendations.branding;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BrandingServiceTest {

  private static BrandingService brandingService;
  private static final String SERVICE_NAME = "BestReads";

  @BeforeAll
  static void setUp() {
    brandingService = new BrandingService(SERVICE_NAME);
  }

  @Test
  void getBranding() {
    assertThat(brandingService.getServiceName())
        .isEqualTo(SERVICE_NAME);
  }
}
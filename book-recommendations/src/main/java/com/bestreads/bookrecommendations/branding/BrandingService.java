package com.bestreads.bookrecommendations.branding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BrandingService {

  @Value("${best-reads.service.name}")
  private String serviceName;

  public String getBranding() {
    return serviceName;
  }

}

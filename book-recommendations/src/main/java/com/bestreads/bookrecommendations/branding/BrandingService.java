package com.bestreads.bookrecommendations.branding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BrandingService {

  private final String serviceName;

  @Autowired
  public BrandingService(@Value("${branding.service.name}") String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceName() {
    return serviceName;
  }

}

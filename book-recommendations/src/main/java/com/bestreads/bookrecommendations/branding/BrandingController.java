package com.bestreads.bookrecommendations.branding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/branding")
public class BrandingController {

  private final BrandingService brandingService;

  @Autowired
  public BrandingController(BrandingService brandingService) {
    this.brandingService = brandingService;
  }

  @GetMapping("/service-name")
  public String getNameForService() {
    return brandingService.getServiceName();
  }

}

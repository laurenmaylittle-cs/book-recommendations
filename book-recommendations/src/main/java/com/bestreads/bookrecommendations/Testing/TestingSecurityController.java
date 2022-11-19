package com.bestreads.bookrecommendations.Testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestingSecurityController {

  @GetMapping("/public/get-public")
  public String returnPublic() {
    return "This is open to public";
  }

  @GetMapping("/private/get-private")
  public String returnPrivate() {
    return "This is only for private stuff";
  }

  @GetMapping("/private-scoped")
  public String privateScoped() {
    return "Scoped private";
  }
}

package com.my.company.endpoint.rest.controller.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@AllArgsConstructor
public class HazavaoController {

  private final OpenAIService openAIService;

  @GetMapping("/hazavao")
    public ResponseEntity<String> getDefinition(@RequestParam String teny) {
      String definition = openAIService.getDefinitionInMalagasy(teny);
      return ResponseEntity.ok(definition);
  }
}

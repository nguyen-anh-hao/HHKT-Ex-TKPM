package org.example.backend.controller;

import org.example.backend.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test-translate")
public class TranslationTestController {

    @Autowired
    private TranslationService translationService;

    @PostMapping("/text")
    public String testTextTranslation(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String from = request.getOrDefault("from", "vi");
        String to = request.getOrDefault("to", "en");

        return translationService.translateText(text, from, to);
    }

    @PostMapping("/object")
    public Object testObjectTranslation(@RequestBody Object request) {
        return translationService.translateObject(request, "en", "vi");
    }
}
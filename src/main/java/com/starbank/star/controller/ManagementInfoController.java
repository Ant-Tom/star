package com.starbank.star.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementInfoController {

    private final BuildProperties buildProperties;

    public ManagementInfoController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of(
                "name", "StarBank Service",
                "version", buildProperties.getVersion()
        );
    }
}

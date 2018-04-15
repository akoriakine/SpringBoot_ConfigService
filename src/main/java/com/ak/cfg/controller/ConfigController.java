package com.ak.cfg.controller;

import com.ak.cfg.model.Config;
import com.ak.cfg.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ConfigController {

    @Autowired private ConfigService configService;

    public ConfigController() {
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{appcode}/config/{version}"
    )
    public String getConfig(@PathVariable("appcode") String appCode,
                            @PathVariable("version") Integer version) {
        return configService.getDocument(appCode, version);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{appcode}/config"
    )
    public List<Config> getConfigByAppCode(@PathVariable("appcode") String appCode) {
        return configService.getVersions(appCode);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{appcode}/config/{version}"
    )

    public void addOrUpdate(@PathVariable("appcode") String appCode,
                            @PathVariable("version") Integer version,
                            @RequestBody String document) {
        configService.update(appCode, version, document);
    }

}

package com.javaprogram.common;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.archaius.DefaultPropertyFactory;
import com.netflix.archaius.api.Config;
import com.netflix.archaius.config.PollingDynamicConfig;
import com.netflix.archaius.config.polling.FixedPollingStrategy;
import com.netflix.archaius.readers.URLConfigReader;

@RestController
public class ConfigController {
    private static final String PROPERTIES_FILE_LOCATION = "file:internal.properties";

    private final Config config = new PollingDynamicConfig(
            new URLConfigReader(PROPERTIES_FILE_LOCATION),
            new FixedPollingStrategy(1, TimeUnit.SECONDS)
    );

    private final DefaultPropertyFactory factory = DefaultPropertyFactory.from(config);

    @GetMapping("/properties/{name}")
    public String findProperty(@PathVariable("name") String name) {
        return factory.get(name, String.class).get();
    }
}

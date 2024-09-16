package com.sph.hyu.mqtt.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.sph.hyu.mqtt.subscriber.tracking.domain")
public class JpaConfig {
}


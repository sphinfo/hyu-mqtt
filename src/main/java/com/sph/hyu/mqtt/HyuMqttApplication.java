package com.sph.hyu.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(scanBasePackages = {"com.sph.hyu.mqtt"})//(exclude = { DataSourceAutoConfiguration.class })
//@ComponentScan(basePackages = {"com.sph.hyu.mqtt", "com.sph.hyu.mqtt.subscriber.tracking"})
@SpringBootApplication
public class HyuMqttApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyuMqttApplication.class, args);
	}

}

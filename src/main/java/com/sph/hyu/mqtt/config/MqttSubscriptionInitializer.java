package com.sph.hyu.mqtt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriptionInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter dozerAdapter;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter rollerAdapter;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter graderAdapter;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                if (dozerAdapter != null) {
                    dozerAdapter.start();
                } else {
                    System.err.println("Dozer adapter is null.");
                }
                
                if (rollerAdapter != null) {
                    rollerAdapter.start();
                } else {
                    System.err.println("Roller adapter is null.");
                }
                
                if (graderAdapter != null) {
                    graderAdapter.start();
                } else {
                    System.err.println("Grader adapter is null.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
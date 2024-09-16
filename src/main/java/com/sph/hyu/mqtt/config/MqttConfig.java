package com.sph.hyu.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.sph.hyu.mqtt.subscriber.dozer.MqttDozerMessageSubscriber;
import com.sph.hyu.mqtt.subscriber.grader.MqttGraderMessageSubscriber;
import com.sph.hyu.mqtt.subscriber.roller.MqttRollerMessageSubscriber;

@Configuration
@EnableIntegration
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.broker.client-id}")
    private String brokerClientId;

    @Value("${mqtt.topic.dozer}")
    private String dozerTopic;

    @Value("${mqtt.topic.roller}")
    private String rollerTopic;

    @Value("${mqtt.topic.grader}")
    private String graderTopic;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(60);
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputDozerChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttInputRollerChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttInputGraderChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter dozerAdapter(MqttPahoClientFactory mqttClientFactory, MessageChannel mqttInputDozerChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                brokerUrl,
                brokerClientId + "-1",
                mqttClientFactory,
                dozerTopic
        );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputDozerChannel);
        adapter.setAutoStartup(false);
        return adapter;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter rollerAdapter(MqttPahoClientFactory mqttClientFactory, MessageChannel mqttInputRollerChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                brokerUrl,
                brokerClientId + "-2",
                mqttClientFactory,
                rollerTopic
        );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputRollerChannel);
        adapter.setAutoStartup(false);
        return adapter;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter graderAdapter(MqttPahoClientFactory mqttClientFactory, MessageChannel mqttInputGraderChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                brokerUrl,
                brokerClientId + "-3",
                mqttClientFactory,
                graderTopic
        );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputGraderChannel);
        adapter.setAutoStartup(false);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputDozerChannel")
    public MessageHandler inboundDozerMessageHandler() {
        return new MqttDozerMessageSubscriber();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputRollerChannel")
    public MessageHandler inboundRollerMessageHandler() {
        return new MqttRollerMessageSubscriber();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputGraderChannel")
    public MessageHandler inboundGraderMessageHandler() {
        return new MqttGraderMessageSubscriber();
    }
}
package br.ifba.ads.workshop.web.configs;

import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@ConditionalOnProperty(value = "spring.thread-executor", havingValue = "virtual")
@ConditionalOnClass(ProtocolHandler.class)
public class WebTomcatThreadConfig {

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            // Use regular thread pool executor for Java 17 compatibility
            protocolHandler.setExecutor(Executors.newCachedThreadPool());
        };
    }
}
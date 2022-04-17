package epruefung.gateway.apigateway.configuration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
//slidingWindowSize nach 10 Aufrufen entscheidet der CircuitBreaker was er machen soll
//failureREate wenn 50% davon failen dann schaltet er sich zu Open
//wait Duration in Open State Nach 30sec versucht er in den Half open State zu gehen
//wenn er in Half Open State ist ( permitted Number of Calls ) 5 Requests werden gesendet um zu schauen wie die lage aussieht
// time Limiter // für jede Request warte ich nicht länger als 1 sec
@Configuration
public class CircuitBreakerConfiguration {
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(20)
                        .permittedNumberOfCallsInHalfOpenState(5)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(30))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(5))
                        .build())
                .build());
    }
}

package epruefung.gateway.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class GatewayController {
    @GetMapping("/modul-fallback")
    Flux<Void> getFallback(){
        return Flux.empty();
    }
}

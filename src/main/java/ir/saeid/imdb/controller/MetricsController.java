package ir.saeid.imdb.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MetricsController {
    @Autowired
    RequestCounter requestCounter;

    @Operation(summary = "counts http request")
    @GetMapping("count")
    public Map countRequests(){
        return Map.of("request-count",requestCounter.getRequestCount());
    }
}

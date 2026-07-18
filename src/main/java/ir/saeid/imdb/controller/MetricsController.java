package ir.saeid.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MetricsController {
    @Autowired
    RequestCounter requestCounter;

    @GetMapping("count")
    public Map countRequests(){
        return Map.of("request-count",requestCounter.getRequestCount());
    }
}

package ir.saeid.imdb.controller;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestCounter implements Filter {
    AtomicLong requestCount = new AtomicLong(0L);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        requestCount.incrementAndGet();
        chain.doFilter(request, response);
    }

    public Long getRequestCount() {
        return requestCount.get();
    }
}

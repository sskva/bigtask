package ru.sskva.bigtask.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class Config {

    private int minCountLines = 1;
    private int maxCountLines = 1_000_000;
    private String urlExternalService = "http://localhost:8081/external-service/getStatus/";
}

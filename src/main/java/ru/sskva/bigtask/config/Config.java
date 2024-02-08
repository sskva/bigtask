package ru.sskva.bigtask.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class Config {

    private int minCountLines = 1;
    private int maxCountLines = 100000;
}

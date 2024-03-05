package ru.sskva.bigtask.gate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sskva.bigtask.config.Config;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClient {

    private final Config config;
    private final ObjectMapper mapper = new ObjectMapper();



    public String call(String inn) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = config.getUrlExternalService();
            ResponseEntity<String> response = restTemplate.getForEntity(url.concat(inn), String.class);

            JsonNode root = mapper.readTree(response.getBody());
            JsonNode name = root.path("data");
            return name.asText();
        } catch (Exception ex) {
            log.error("error RestClient ", ex);
            return "INTERNAL_SERVICE_ERROR";
        }
    }
}

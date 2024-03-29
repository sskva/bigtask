package ru.sskva.bigtask.work.one_thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.gate.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OneThread {

    private final RestClient restClient;


    public void process(List<CheckInn> checkInnList) {

        log.info("oneThread started");
        for (CheckInn checkInn : checkInnList) {
            String status = restClient.call(checkInn.getInn());
            checkInn.setStatus(status);
        }
        log.info("oneThread ended");
    }
}


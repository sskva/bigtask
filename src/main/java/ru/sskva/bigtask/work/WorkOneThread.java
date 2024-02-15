package ru.sskva.bigtask.work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.gate.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOneThread {

    private final RestClient restClient;


    public List<CheckInn> work(List<CheckInn> checkInnList) {

        log.info("WorkOneThread started");

        for (CheckInn checkInn : checkInnList) {
            String status = restClient.call(checkInn.getInn());
            checkInn.setStatusCode(status);
        }

        log.info("WorkOneThread ended");
        return checkInnList;
    }
}

package ru.sskva.bigtask.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.gate.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobOneThread {

    private final Dao dao;
    private final RestClient restClient;



    @Scheduled(cron = "*/1 * * * * *")
    public void jobCheck() {

        log.info("jobCheck started");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CheckInn> checkInnList = dao.getInn();
        log.info("checkInnList.size(): {}", checkInnList.size());
        if (checkInnList.size() == 0) {
            dao.setFileStatusProcessed();
            return;
        }

        List<CheckInn> checkInnListAnswer = workOneThread(checkInnList);
        log.info("checkInnListAnswer: {}", checkInnListAnswer);
        dao.saveResult(checkInnListAnswer);

        stopWatch.stop();
        log.info("jobCheck ended, time work: {}", stopWatch.getTime() / 1000);
    }



    public List<CheckInn> workOneThread(List<CheckInn> checkInnList) {

        log.info("workOneThread started");

        for (CheckInn checkInn : checkInnList) {
            String status = restClient.call(checkInn.getInn());
            checkInn.setStatusCode(status);
        }

        log.info("workOneThread ended");
        return checkInnList;
    }
}









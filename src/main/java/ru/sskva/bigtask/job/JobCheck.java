package ru.sskva.bigtask.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.fjp.Fjp;
import ru.sskva.bigtask.gate.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCheck {

    private final Fjp fjp;
    private final Dao dao;
    private final RestClient restClient;



    @Scheduled(cron = "*/1 * * * * *")
    private void jobCheck() {

        log.info("jobCheck started");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CheckInn> checkInnList = dao.getInn();
        log.info("checkInnList.size(): {}", checkInnList.size());

        if (checkInnList.size() == 0) {
            dao.setFileStatusProcessed();
            log.info("jobCheck ended");
            return;
        }

        workForkJoinPool(checkInnList);
        log.info("checkInnListAnswer: {}", checkInnList);
        dao.saveResult(checkInnList);

        stopWatch.stop();
        log.info("jobCheck ended, time work: {}", stopWatch.getTime() / 1000);
    }



    private void workForkJoinPool(List<CheckInn> checkInnList) {

        fjp.process(checkInnList);
    }
}









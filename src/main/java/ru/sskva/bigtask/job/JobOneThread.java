package ru.sskva.bigtask.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.work.WorkOneThread;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobOneThread {

    private final Dao dao;
    private final WorkOneThread worker;




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

        List<CheckInn> checkInnListAnswer = worker.work(checkInnList);
        log.info("checkInnListAnswer: {}", checkInnListAnswer);
        dao.saveResult(checkInnListAnswer);

        stopWatch.stop();
        log.info("jobCheck ended, time work: {}", stopWatch.getTime() / 1000);
    }
}









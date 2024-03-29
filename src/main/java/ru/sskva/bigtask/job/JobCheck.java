package ru.sskva.bigtask.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.work.fjp_ra.FjpRa;
import ru.sskva.bigtask.work.fjp_rt.FjpRt;
import ru.sskva.bigtask.work.one_thread.OneThread;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCheck {

    private final FjpRt fjpRt;
    private final FjpRa fjpRa;
    private final OneThread oneThread;
    private final Dao dao;



    @Scheduled(cron = "*/1 * * * * *")
    private void jobCheck() {

        log.info("jobCheck started");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CheckInn> checkInnList = dao.getInn();
        log.info("checkInnList.size(): {}", checkInnList.size());

        if (checkInnList.isEmpty()) {
            dao.setFileStatusProcessed();
            log.info("jobCheck ended");
            return;
        }

        List<CheckInn> checkInnListAnswer = fjpRt.process(checkInnList);
        log.info("checkInnListAnswer: {}", checkInnListAnswer);
        dao.saveResult(checkInnListAnswer);

        stopWatch.stop();
        log.info("jobCheck ended, time work: {}", stopWatch.getTime() / 1000);
    }
}









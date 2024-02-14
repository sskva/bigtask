package ru.sskva.bigtask.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobOneThread {

//    @Scheduled(cron = "*/1 * * * * *")
    public void jobCheck() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        stopWatch.stop();
        log.info("jobCheck ended, time work: {}", stopWatch.getTime() / 1000);
    }
}

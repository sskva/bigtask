package ru.sskva.bigtask.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCheck {

    @Scheduled(cron = "*/5 * * * * *")
    public void jobCheck() {

        log.info("jobCheck start");
        long timeStart = System.currentTimeMillis();



        long timeWork = (System.currentTimeMillis() - timeStart) / 1000;
        log.info("jobCheck end, timeWork: {} seconds", timeWork);
    }
}

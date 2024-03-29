package ru.sskva.bigtask.work.fjp_rt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.config.Config;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@Component
@RequiredArgsConstructor
public class FjpRt {

    private final Config config;
    private ForkJoinPool pool;



    @PostConstruct
    private void initialize() {
        pool = new ForkJoinPool(config.getCountWorkers());
    }



    public List<CheckInn> process(List<CheckInn> checkInnList) {

        log.info("fjp started");
        Task task = new Task(checkInnList);
        List<CheckInn> checkInnListAnswer = pool.invoke(task);
        log.info("fjp ended");
        return checkInnListAnswer;
    }
}
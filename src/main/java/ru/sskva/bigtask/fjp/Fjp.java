package ru.sskva.bigtask.fjp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.config.Config;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Component
@RequiredArgsConstructor
public class Fjp {

    private final Config config;



    public void process(List<CheckInn> checkInnList) {

        ForkJoinPool pool = new ForkJoinPool(config.getCountWorkers());
        Task task = new Task(checkInnList);
        pool.invoke(task);
    }
}

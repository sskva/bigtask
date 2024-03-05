package ru.sskva.bigtask.fjp;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


@Slf4j
public class Task extends RecursiveTask<List<CheckInn>> {

    private final List<CheckInn> checkInnList;



    public Task(List<CheckInn> checkInnList) {
        this.checkInnList = checkInnList;
    }



    @SneakyThrows
    @Override
    protected List<CheckInn> compute() {

        List<ForkJoinTask<CheckInn>> subTaskList = new ArrayList<>();

        for (CheckInn checkInn : checkInnList) {
            ForkJoinTask<CheckInn> subTask = new SubTask(checkInn);
            subTaskList.add(subTask);
            subTask.fork();
            log.info("fork, id: {}", checkInn.getId());
        }

        List<CheckInn> result = new ArrayList<>();
        for (ForkJoinTask<CheckInn> subTask : subTaskList) {
            result.add(subTask.join());
            log.info("join, id: {}", subTask.get().getId());
        }
        return result;
    }
}

package ru.sskva.bigtask.work.fjp_rt;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;


@Slf4j
public class Task extends RecursiveTask<List<CheckInn>> {

    private final List<CheckInn> checkInnList;



    public Task(List<CheckInn> checkInnList) {
        this.checkInnList = checkInnList;
    }



    @Override
    @SneakyThrows
    protected List<CheckInn> compute() {

        List<SubTask> subTaskList = new ArrayList<>();

        for (CheckInn checkInn : checkInnList) {
            SubTask subTask = new SubTask(checkInn);
            subTaskList.add(subTask);
            subTask.fork();
            log.info("fork, id: {}", checkInn.getId());
        }

        List<CheckInn> checkInnListAnswer = new ArrayList<>();
        for (SubTask subTask : subTaskList) {
            CheckInn checkInn = subTask.join();
            checkInnListAnswer.add(checkInn);
            log.info("join, id: {}", subTask.get().getId());
        }
        return checkInnListAnswer;
    }
}

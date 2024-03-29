package ru.sskva.bigtask.work.fjp_ra;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;


@Slf4j
public class Task extends RecursiveAction {

    private final List<CheckInn> checkInnList;



    public Task(List<CheckInn> checkInnList) {
        this.checkInnList = checkInnList;
    }



    @Override
    @SneakyThrows
    protected void compute() {

        List<SubTask> subTaskList = new ArrayList<>();

        for (CheckInn checkInn : checkInnList) {
            SubTask subTask = new SubTask(checkInn);
            subTaskList.add(subTask);
            subTask.fork();
            log.info("fork, id: {}", checkInn.getId());
        }

        for (SubTask subTask : subTaskList) {
            subTask.join();
        }
    }
}

package ru.sskva.bigtask.work.fjp_rt;

import lombok.extern.slf4j.Slf4j;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.gate.RestClient;
import ru.sskva.bigtask.util.SpringContext;

import java.util.concurrent.RecursiveTask;

@Slf4j
public class SubTask extends RecursiveTask<CheckInn> {

    private final CheckInn checkInn;



    public SubTask(CheckInn checkInn) {
        this.checkInn = checkInn;
    }



    @Override
    protected CheckInn compute() {

        log.info("start subTask, id: {}", checkInn.getId());
        RestClient restClient = SpringContext.getBean(RestClient.class);
        String status = restClient.call(checkInn.getInn());
        checkInn.setStatus(status);
        log.info("end subTask, id: {}, status: {}", checkInn.getId(), checkInn.getStatus());
        return checkInn;
    }
}

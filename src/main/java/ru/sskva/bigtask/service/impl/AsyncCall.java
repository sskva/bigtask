package ru.sskva.bigtask.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.sskva.bigtask.domain.dto.MassCheckItem;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncCall {

    private final AsyncSaveInn asyncSaveInn;



    @Async
    public void saveInnCheckAdvanced(List<MassCheckItem> massCheckItemList, String fileId) {

        asyncSaveInn.saveInn(massCheckItemList, fileId);
    }
}

package ru.sskva.bigtask.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sskva.bigtask.config.Config;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.dto.MassCheckItem;
import ru.sskva.bigtask.domain.response.Response;
import ru.sskva.bigtask.domain.response.SuccessResponse;
import ru.sskva.bigtask.exception.ExistsFileInProcessOrLoadingException;
import ru.sskva.bigtask.exception.WrongCountLinesException;
import ru.sskva.bigtask.service.LogicService;
import ru.sskva.bigtask.util.CSVProcessor;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogicServiceImpl implements LogicService {

    private final Dao dao;
    private final AsyncSaveInn asyncSaveInn;
    private final Config config;

    @Override
    public Response saveInnCSV(MultipartFile file)  {

        if (dao.ifExistsFileInProcessOrLoading())
            throw new ExistsFileInProcessOrLoadingException();

        List<MassCheckItem> massCheckItemList = CSVProcessor.getListFromCSV(file, MassCheckItem.builder().build());
        log.info("count lines: {}", massCheckItemList.size());

        if (massCheckItemList.size() < config.getMinCountLines() || massCheckItemList.size() > config.getMaxCountLines())
            throw new WrongCountLinesException();

        String fileId = UUID.randomUUID().toString().replace("-", "");
        dao.saveFileInfo(fileId, massCheckItemList.size());
        asyncSaveInn.saveInn(massCheckItemList, fileId);

        return SuccessResponse.builder().data(fileId).build();
    }
}

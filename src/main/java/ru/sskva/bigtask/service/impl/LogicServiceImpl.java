package ru.sskva.bigtask.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.dto.MassCheckItem;
import ru.sskva.bigtask.domain.response.Response;
import ru.sskva.bigtask.domain.response.SuccessResponse;
import ru.sskva.bigtask.exception.ExistsFileInProgressException;
import ru.sskva.bigtask.exception.WrongFileSizeException;
import ru.sskva.bigtask.service.LogicService;
import ru.sskva.bigtask.util.CSVProcessor;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class LogicServiceImpl implements LogicService {

    private final Dao dao;
    private final AsyncCall asyncCall;

    @Override
    public Response massCheckCSV(MultipartFile file)  {

        if (dao.ifExistsFileInProgress())
            throw new ExistsFileInProgressException();

        List<MassCheckItem> massCheckItemList = CSVProcessor.getListFromCSV(file, MassCheckItem.builder().build());
        log.info("count lines: {}", massCheckItemList.size());

        if (massCheckItemList.size() == 0 || massCheckItemList.size() > 100000)
            throw new WrongFileSizeException();

        String fileId = UUID.randomUUID().toString().replace("-", "");
        dao.saveFileInfo(fileId, massCheckItemList.size());
        asyncCall.saveInnCheckAdvanced(massCheckItemList, fileId);

        return SuccessResponse.builder().data(fileId).build();
    }
}

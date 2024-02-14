package ru.sskva.bigtask.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sskva.bigtask.domain.response.Response;
import ru.sskva.bigtask.service.LogicService;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bigtask-service")
public class Controller {

    private final LogicService logicService;

    @PostMapping(value = "checkInnCSV", consumes = MULTIPART_FORM_DATA, produces = "application/json;charset=UTF-8")
    public Response checkInnCSV(@RequestParam("file") MultipartFile file) {

        log.info("ENDPOINT_CHECK_INN_CSV_START. File name: {}, ContentType: {}, File size: {}", file.getOriginalFilename(), file.getContentType(), file.getSize());
        Response response = logicService.saveInnCSV(file);
        log.info("ENDPOINT_CHECK_INN_CSV_END: {}", response);
        return response;
    }
}

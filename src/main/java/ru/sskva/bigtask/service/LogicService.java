package ru.sskva.bigtask.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sskva.bigtask.domain.response.Response;

public interface LogicService {

    Response massCheckCSV(MultipartFile file);
}

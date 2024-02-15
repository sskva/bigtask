package ru.sskva.bigtask.dao;

import ru.sskva.bigtask.domain.dto.Inn;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.util.List;

public interface Dao {

    boolean ifExistsFileInProcessOrLoading();

    void saveFileInfo(String fileId, int size);

    void saveInn(List<Inn> innList, String fileId);

    List<CheckInn> getInn();

    void setFileStatusProcessed();

    void saveResult(List<CheckInn> checkInnList);
}

package ru.sskva.bigtask.dao;

import ru.sskva.bigtask.domain.dto.MassCheckItem;

import java.util.List;

public interface Dao {

    boolean ifExistsFileInProcessOrLoading();

    void saveFileInfo(String fileId, int size);

    void saveInn(List<MassCheckItem> massCheckItemList, String fileId);
}

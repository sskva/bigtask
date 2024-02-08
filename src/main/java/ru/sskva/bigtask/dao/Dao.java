package ru.sskva.bigtask.dao;

public interface Dao {

    boolean ifExistsFileInProcessOrLoading();

    void saveFileInfo(String fileId, int size);
}

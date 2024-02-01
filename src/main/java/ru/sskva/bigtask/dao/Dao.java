package ru.sskva.bigtask.dao;

public interface Dao {

    boolean ifExistsFileInProgress();

    void saveFileInfo(String fileId, int size);
}

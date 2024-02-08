package ru.sskva.bigtask.dao.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.constant.Status;

import javax.sql.DataSource;

@Slf4j
@Repository
@Transactional
public class DaoImpl extends JdbcDaoSupport implements Dao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public boolean ifExistsFileInProcessOrLoading() {
        return getJdbcTemplate().queryForObject("SELECT (SELECT count(*) FROM file_info WHERE status = ? OR status = ?) > 0 AS result;",
                Boolean.class, Status.IN_PROCESS.toString(), Status.LOADING.toString());
    }

    @Override
    public void saveFileInfo(String fileId, int size) {

    }
}

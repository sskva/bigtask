package ru.sskva.bigtask.dao.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sskva.bigtask.dao.Dao;
import ru.sskva.bigtask.domain.constant.Status;
import ru.sskva.bigtask.domain.dto.MassCheckItem;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        return getJdbcTemplate().queryForObject("SELECT (SELECT count(*) FROM file_info WHERE status = ? OR status = ?) > 0 AS result;", Boolean.class, Status.IN_PROCESS.toString(), Status.LOADING.toString());
    }



    @Override
    public void saveFileInfo(String fileId, int size) {
        getJdbcTemplate().update("INSERT INTO file_info(file_id, count_record, status) VALUES (?,?,?);", fileId, size, Status.LOADING.toString());
    }



    @Override
    public void saveInn(List<MassCheckItem> massCheckItemList, String fileId) {

        log.info("saveInn started");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        getJdbcTemplate().batchUpdate(
                "INSERT INTO check_inn(file_id, inn, status_code) VALUES (?,?,?);",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, fileId);
                        ps.setString(2, massCheckItemList.get(i).getInn());
                        ps.setString(3, Status.IN_PROCESS.toString());
                    }



                    @Override
                    public int getBatchSize() {
                        return massCheckItemList.size();
                    }
                });

        getJdbcTemplate().update("UPDATE file_info SET status = ? WHERE file_id = ?;",
                Status.IN_PROCESS.toString(), fileId);

        stopWatch.stop();
        log.info("saveInn ended, time work: {} seconds", stopWatch.getTime() / 1000);
    }
}
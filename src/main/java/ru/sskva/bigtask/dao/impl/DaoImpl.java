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
import ru.sskva.bigtask.domain.dto.Inn;
import ru.sskva.bigtask.domain.entity.CheckInn;
import ru.sskva.bigtask.domain.rowmapper.CheckInnRowMapper;

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
    public void saveResult(List<CheckInn> checkInnList) {

        getJdbcTemplate().batchUpdate(
                "UPDATE check_inn SET status = ? WHERE id = ?;",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, checkInnList.get(i).getStatus());
                        ps.setLong(2, checkInnList.get(i).getId());
                    }



                    @Override
                    public int getBatchSize() {
                        return checkInnList.size();
                    }
                });
    }



    @Override
    public void setFileStatusProcessed() {
        getJdbcTemplate().update("UPDATE file_info SET status = ?, time_end = NOW() WHERE status = ?;",
                Status.PROCESSED.toString(), Status.IN_PROCESS.toString());
    }



    @Override
    public boolean ifExistsFileInProcessOrLoading() {
        return getJdbcTemplate().queryForObject("SELECT (SELECT count(*) FROM file_info WHERE status = ? OR status = ?) > 0 AS result;",
                Boolean.class, Status.IN_PROCESS.toString(), Status.LOADING.toString());
    }



    @Override
    public List<CheckInn> getInn() {

        return getJdbcTemplate().query("SELECT * " +
                "FROM check_inn " +
                "WHERE file_id = (SELECT file_id FROM file_info WHERE status = ?) " +
                "  AND status = ? " +
                "ORDER BY id LIMIT 100;", new CheckInnRowMapper(), Status.IN_PROCESS.toString(), Status.IN_PROCESS.toString());
    }



    @Override
    public void saveFileInfo(String fileId, int size) {
        getJdbcTemplate().update("INSERT INTO file_info(file_id, count_lines, status) VALUES (?,?,?);", fileId, size, Status.LOADING.toString());
    }



    @Override
    public void saveInn(List<Inn> innList, String fileId) {

        log.info("saveInn started");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        getJdbcTemplate().batchUpdate(
                "INSERT INTO check_inn(file_id, inn, status) VALUES (?,?,?);",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, fileId);
                        ps.setString(2, innList.get(i).getInn());
                        ps.setString(3, Status.IN_PROCESS.toString());
                    }



                    @Override
                    public int getBatchSize() {
                        return innList.size();
                    }
                });

        getJdbcTemplate().update("UPDATE file_info SET status = ? WHERE file_id = ?;",
                Status.IN_PROCESS.toString(), fileId);

        stopWatch.stop();
        log.info("saveInn ended, time work: {} seconds", stopWatch.getTime() / 1000);
    }
}
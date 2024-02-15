package ru.sskva.bigtask.domain.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.sskva.bigtask.domain.entity.CheckInn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInnRowMapper implements RowMapper<CheckInn> {

    @Override
    public CheckInn mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CheckInn.builder()
                .id(rs.getLong("id"))
                .fileId(rs.getString("file_id"))
                .timeInsert(rs.getString("time_insert"))
                .inn(rs.getString("inn"))
                .statusCode(rs.getString("status_code"))
                .build();
    }
}
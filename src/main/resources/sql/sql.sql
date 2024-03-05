CREATE TABLE IF NOT EXISTS file_info
(
    id              BIGINT AUTO_INCREMENT,
    file_id         VARCHAR(400) NOT NULL UNIQUE,
    time_start      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    time_end        TIMESTAMP             DEFAULT NULL,
    status          VARCHAR(400)          DEFAULT NULL,
    count_lines     INT                   DEFAULT NULL,
    PRIMARY KEY (id)
) COLLATE utf8_bin;

CREATE TABLE IF NOT EXISTS check_inn
(
    id             BIGINT AUTO_INCREMENT,
    file_id        VARCHAR(400) NOT NULL,
    time_insert    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    inn            VARCHAR(400),
    status         VARCHAR(400)          DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (file_id) REFERENCES file_info (file_id)
) COLLATE utf8_bin;
















SELECT count(*)
FROM check_inn
WHERE file_id = 'b34e48b92ccb4fb89aab7308ca5d02bd';

DELETE
FROM check_inn
WHERE id > 0;

DELETE
FROM file_info
WHERE id > 0;

SELECT id,
       file_id,
       count_lines,
       TIME_TO_SEC(TIMEDIFF(time_end, time_start))                AS time_work,
       count_lines / TIME_TO_SEC(TIMEDIFF(time_end, time_start)) AS lines_per_second
FROM file_info;
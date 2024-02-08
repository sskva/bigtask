CREATE TABLE IF NOT EXISTS file_info
(
    id              BIGINT AUTO_INCREMENT,
    file_id         VARCHAR(400) NOT NULL UNIQUE,
    time_start TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    time_end   TIMESTAMP             DEFAULT NULL,
    status          VARCHAR(400)          DEFAULT NULL,
    count_record    INT                   DEFAULT NULL,
    PRIMARY KEY (id)
) COLLATE utf8_bin;

CREATE TABLE IF NOT EXISTS check_inn
(
    id             BIGINT AUTO_INCREMENT,
    file_id        VARCHAR(400) NOT NULL,
    time_insert    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    inn            VARCHAR(400),
    status_code    VARCHAR(400)          DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (file_id) REFERENCES file_info (file_id)
) COLLATE utf8_bin;
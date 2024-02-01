package ru.sskva.bigtask.domain.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MassCheckItem {

    @CsvBindByPosition(position = 0)
    private String inn;
}

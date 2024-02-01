package ru.sskva.bigtask.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

public class CSVProcessor {


    public static <T> List<T> getListFromCSV(MultipartFile file, T data) {

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            return new CsvToBeanBuilder(reader).withType(data.getClass()).withSeparator((Character) ';').build().parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T, data> void createAnswerCSV(List<T> itemList, HttpServletResponse response, T data) {

        try (PrintWriter writer = response.getWriter()) {
            StatefulBeanToCsv<data> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator((Character) ';').build();
            beanToCsv.write((List<data>) itemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

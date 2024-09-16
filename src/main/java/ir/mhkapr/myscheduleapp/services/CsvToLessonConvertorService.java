package ir.mhkapr.myscheduleapp.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ir.mhkapr.myscheduleapp.objects.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvToLessonConvertorService {
    public List<Lesson> convertCsvToLessons(String filePath) throws IOException, CsvValidationException {
        List<Lesson> lessons = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            // Skip the header if present
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                Lesson lesson = Lesson.builder()
                        .name(values[0])
                        .TeacherName(values[1])
                        .unit(Integer.parseInt(values[2]))
                        .day1(values[3].isEmpty() ? null : Integer.parseInt(values[3]))
                        .hour1(values[4].isEmpty() ? null : Integer.parseInt(values[4]))
                        .eachOtherWeek1(values[5].isEmpty() ? null : Integer.parseInt(values[5]))
                        .day2(values[6].isEmpty() ? null : Integer.parseInt(values[6]))
                        .hour2(values[7].isEmpty() ? null : Integer.parseInt(values[7]))
                        .eachOtherWeek2(values[8].isEmpty() ? null : Integer.parseInt(values[8]))
                        .isReligious(Boolean.parseBoolean(values[9]))
                        .hasLab(Boolean.parseBoolean(values[10]))
                        .labName(values[11].isEmpty() ? null : values[11])
                        .labTeacherName(values[12].isEmpty() ? null : values[12])
                        .labUnit(values[13].isEmpty() ? null : Integer.parseInt(values[13]))
                        .labDay(values[14].isEmpty() ? null : Integer.parseInt(values[14]))
                        .labHour(values[15].isEmpty() ? null : Integer.parseInt(values[15]))
                        .build();
                lessons.add(lesson);
            }
        }

        return lessons;
    }

    public List<Lesson> convertCsvToLessons(MultipartFile multipartFile) throws IOException, CsvValidationException {
        List<Lesson> lessons = new ArrayList<>();

        Reader reader = new InputStreamReader(multipartFile.getInputStream());

        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] values;
            // Skip the header if present
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                Lesson lesson = Lesson.builder()
                        .name(values[0])
                        .TeacherName(values[1])
                        .unit(Integer.parseInt(values[2]))
                        .day1(values[3].isEmpty() ? null : Integer.parseInt(values[3]))
                        .hour1(values[4].isEmpty() ? null : Integer.parseInt(values[4]))
                        .eachOtherWeek1(values[5].isEmpty() ? null : Integer.parseInt(values[5]))
                        .day2(values[6].isEmpty() ? null : Integer.parseInt(values[6]))
                        .hour2(values[7].isEmpty() ? null : Integer.parseInt(values[7]))
                        .eachOtherWeek2(values[8].isEmpty() ? null : Integer.parseInt(values[8]))
                        .isReligious(Boolean.parseBoolean(values[9]))
                        .hasLab(Boolean.parseBoolean(values[10]))
                        .labName(values[11].isEmpty() ? null : values[11])
                        .labTeacherName(values[12].isEmpty() ? null : values[12])
                        .labUnit(values[13].isEmpty() ? null : Integer.parseInt(values[13]))
                        .labDay(values[14].isEmpty() ? null : Integer.parseInt(values[14]))
                        .labHour(values[15].isEmpty() ? null : Integer.parseInt(values[15]))
                        .build();
                lessons.add(lesson);
            }
        }
        return lessons;
    }
}

package ir.mhkapr.myscheduleapp.controllers;

import com.opencsv.exceptions.CsvValidationException;
import ir.mhkapr.myscheduleapp.DTOs.SubmitDataRequest;
import ir.mhkapr.myscheduleapp.DTOs.SubmitDataResponse;
import ir.mhkapr.myscheduleapp.objects.Lesson;
import ir.mhkapr.myscheduleapp.services.CsvToLessonConvertorService;
import ir.mhkapr.myscheduleapp.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class scheduleController {

    private final ScheduleService scheduleService;
    private final CsvToLessonConvertorService csvToLessonConverter;

    @PostMapping("/submit-data")
    public ResponseEntity<SubmitDataResponse> submitData(@RequestBody SubmitDataRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(scheduleService
                .buildSchedules(request.getUnit(), request.getLessons()));
    }

    @GetMapping("/convert")
    public ResponseEntity<SubmitDataResponse> convertCsvToJson(@RequestParam String filePath)
            throws IOException, CsvValidationException, ExecutionException, InterruptedException {
        List<Lesson> lessons = csvToLessonConverter.convertCsvToLessons(filePath);
        return ResponseEntity.ok(scheduleService
                .buildSchedules(15, lessons));
    }
}

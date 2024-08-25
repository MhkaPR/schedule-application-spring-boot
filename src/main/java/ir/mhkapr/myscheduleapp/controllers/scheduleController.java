package ir.mhkapr.myscheduleapp.controllers;

import ir.mhkapr.myscheduleapp.DTOs.SubmitDataRequest;
import ir.mhkapr.myscheduleapp.DTOs.SubmitDataResponse;
import ir.mhkapr.myscheduleapp.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class scheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/submit-data")
    public ResponseEntity<SubmitDataResponse> submitData(@RequestBody SubmitDataRequest request) {
        return ResponseEntity.ok(scheduleService
                .buildSchedules(request.getMin(), request.getMax(), request.getLessons()));
    }
}

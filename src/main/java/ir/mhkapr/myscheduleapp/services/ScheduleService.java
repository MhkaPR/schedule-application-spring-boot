package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.DTOs.SubmitDataResponse;
import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private List<List<Lesson>> powerSet(List<Lesson> lessons, Integer unit) {
       
    }

    public SubmitDataResponse buildSchedules(Integer unit, List<Lesson> lessonList) {

    }

}

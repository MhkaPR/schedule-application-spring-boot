package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.DTOs.SubmitDataResponse;
import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private List<List<Lesson>> powerSet(List<Lesson> lessons, Integer unit) {

    }
    public SubmitDataResponse buildSchedules(Integer unit, List<Lesson> lessonList)
            throws ExecutionException, InterruptedException {
        return SubmitDataResponse.builder().schedules(powerSet(lessonList, unit)).build();
    }

}

package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.DTOs.SubmitDataResponse;
import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    final ScheduleFinderService scheduleFinderService;

    private List<Lesson[]> powerSet(List<Lesson> lessons, Integer unit) {
        Map<String, List<Lesson>> categorizedLessons = lessons.parallelStream()
                .collect(Collectors.groupingBy(Lesson::getName));

        List<List<Lesson>> listLessons = categorizedLessons.keySet().parallelStream().map(x -> {
            List<Lesson> lessonStack = new Stack<>();
            lessonStack.addAll(categorizedLessons.get(x));
            return lessonStack;
        }).toList();

        return scheduleFinderService.generateCombinations(listLessons, unit);
    }
    public SubmitDataResponse buildSchedules(Integer unit, List<Lesson> lessonList)
            throws ExecutionException, InterruptedException {
        return SubmitDataResponse.builder().schedules(powerSet(lessonList, unit)).build();
    }

}

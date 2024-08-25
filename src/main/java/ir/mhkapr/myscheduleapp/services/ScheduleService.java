package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private List<List<Lesson>> powerSet(List<Lesson> lessonList, Integer min, Integer max) {
        SetService setService = new SetService(lessonList);
        List<List<Integer>> ListOfIndexPlans = setService.calculateByRange(min, max);
        return ListOfIndexPlans.stream()
                .map(indexList -> indexList.stream().map(lessonList::get).toList()).toList();
    }

}

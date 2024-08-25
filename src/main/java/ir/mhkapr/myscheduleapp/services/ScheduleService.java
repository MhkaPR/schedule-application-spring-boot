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

    public List<List<Lesson>> buildSchedules(Integer min, Integer max) {

    }

    private Boolean hasConflict(List<Lesson> lessonList) {

    }

    private Boolean hasOneReligiousLesson(List<Lesson> lessonList) {

    }

    private Boolean existsRepetition(List<Lesson> lessonList) {
        return lessonList.stream().map(Lesson::getName).distinct().count() != lessonList.size();
    }
}

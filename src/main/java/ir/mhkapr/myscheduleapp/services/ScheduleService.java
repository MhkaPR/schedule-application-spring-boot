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

    public List<List<Lesson>> buildSchedules(Integer min, Integer max, List<Lesson> lessonList) {
        List<List<Lesson>> listOfSuggestedPlans = powerSet(lessonList, min, max);
        return listOfSuggestedPlans.stream().filter(x -> {
            return !(hasConflict(x) || existsRepetition(x) || hasManyReligiousLessons(x));
        }).toList();

    }

    private Boolean hasConflict(List<Lesson> lessonList) {

    }

    private Boolean doLessonsOverlap(Lesson lesson1, Lesson lesson2) {

        //first by first
        if (lesson1.getDay1().equals(lesson2.getDay1())) {
            if (lesson1.getHour1().equals(lesson2.getHour1())) {
                if (lesson1.getEachOtherWeek1() == 1 && lesson2.getEachOtherWeek1() == 1) {
                    return true;
                } else if (lesson1.getEachOtherWeek1() == 2 && lesson2.getEachOtherWeek1() == 2) {
                    return true;
                }
            }
        }
        //secend by first
        if (lesson1.getDay2().equals(lesson2.getDay1())) {
            if (lesson1.getHour2().equals(lesson2.getHour1())) {
                if (lesson1.getEachOtherWeek1() == 1 && lesson2.getEachOtherWeek1() == 1) {
                    return true;
                } else if (lesson1.getEachOtherWeek1() == 2 && lesson2.getEachOtherWeek1() == 2) {
                    return true;
                }
            }
        }
        //first by secend
        if (lesson1.getDay1().equals(lesson2.getDay2())) {
            if (lesson1.getHour1().equals(lesson2.getHour2())) {
                if (lesson1.getEachOtherWeek1() == 1 && lesson2.getEachOtherWeek1() == 1) {
                    return true;
                } else if (lesson1.getEachOtherWeek1() == 2 && lesson2.getEachOtherWeek1() == 2) {
                    return true;
                }
            }
        }
        //secend by secend
        if (lesson1.getDay2().equals(lesson2.getDay2())) {
            if (lesson1.getHour2().equals(lesson2.getHour2())) {
                if (lesson1.getEachOtherWeek1() == 1 && lesson2.getEachOtherWeek1() == 1) {
                    return true;
                } else if (lesson1.getEachOtherWeek1() == 2 && lesson2.getEachOtherWeek1() == 2) {
                    return true;
                }
            }
        }
        return hasConflictLabTime(lesson1, lesson2);
    }

    private Boolean hasConflictLabTime(Lesson lesson1, Lesson lesson2) {
        if (!lesson1.getHasLab() && !lesson2.getHasLab()) return false;
        else {
            //first lab by first
            if (lesson1.getHasLab()) {
                if (lesson1.getLabDay().equals(lesson2.getDay1())) {
                    if (lesson1.getLabHour().equals(lesson2.getHour1())) {
                        return true;
                    }
                }
                //first lab by secend
                if (lesson1.getLabDay().equals(lesson2.getDay2())) {
                    if (lesson1.getLabHour().equals(lesson2.getHour2())) {
                        return true;
                    }
                }
            }

            if (lesson2.getHasLab()) {
                //first by secend lab
                if (lesson1.getDay1().equals(lesson2.getLabDay())) {
                    if (lesson1.getHour1().equals(lesson2.getLabHour())) {
                        return true;
                    }
                }
                //secend by secend lab
                if (lesson1.getDay2().equals(lesson2.getLabDay())) {
                    if (lesson1.getHour2().equals(lesson2.getLabHour())) {
                        return true;
                    }
                }
            }

            if (lesson1.getHasLab() && lesson2.getHasLab()) {
                if (lesson1.getLabDay().equals(lesson2.getLabDay())) {
                    if (lesson1.getLabHour().equals(lesson2.getLabHour())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private Boolean hasManyReligiousLessons(List<Lesson> lessonList) {
        return lessonList.stream().filter(Lesson::getIsReligious).count() > 1;
    }

    private Boolean existsRepetition(List<Lesson> lessonList) {
        return lessonList.stream().map(Lesson::getName).distinct().count() != lessonList.size();
    }
}

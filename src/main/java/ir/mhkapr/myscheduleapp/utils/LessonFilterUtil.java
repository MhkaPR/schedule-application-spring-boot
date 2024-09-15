package ir.mhkapr.myscheduleapp.utils;

import ir.mhkapr.myscheduleapp.objects.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonFilterUtil {
    public static Boolean hasConflict(List<Lesson> lessonList) {
        Boolean sw = false;
        for (int i = 0; i < lessonList.size(); i++) {
            for (int j = i + 1; j < lessonList.size(); j++) {
                sw = doLessonsOverlap(lessonList.get(i), lessonList.get(j));
                if (sw) {
                    i = lessonList.size();
                    break;
                }
            }
        }
        return sw;
    }

    public static Boolean doLessonsOverLap(Lesson lessonForCheck, List<Lesson> lessonList) {
        for (Lesson lesson : lessonList) {
            if (doLessonsOverlap(lesson, lessonForCheck)) return false;
        }
        return true;
    }

    public static Boolean doLessonsOverlap(Lesson lesson1, Lesson lesson2) {

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

    public
    static Boolean hasConflictLabTime(Lesson lesson1, Lesson lesson2) {
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

    public static Boolean hasManyReligiousLessons(List<Lesson> lessonList) {
        return lessonList.stream().filter(Lesson::getIsReligious).count() > 1;
    }

    public static Boolean existsRepetition(List<Lesson> lessonList) {
        return lessonList.stream().map(Lesson::getName).distinct().count() != lessonList.size();
    }

    public static Boolean hasNotUnit(int unitSum, List<Lesson> lessonList) {
        return lessonList.stream().mapToInt(Lesson::getUnit).sum() != unitSum;
    }
}

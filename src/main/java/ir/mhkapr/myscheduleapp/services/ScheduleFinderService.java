package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.objects.Lesson;
import ir.mhkapr.myscheduleapp.utils.LessonFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleFinderService {
    List<List<Lesson>> answer;
    int desiredUnit = 0;

    private void generateCombinations(List<List<Lesson>> lists, Lesson[] combination,
                                      int index, List<Lesson[]> result) {
        if (index == lists.size()) {
            if (isCorrectPlan(Arrays.stream(combination).filter(Objects::nonNull).toList()))
                result.add(combination.clone());
            return;
        }
        List<Lesson> currentList = lists.get(index);
        combination[index] = null;
        generateCombinations(lists, combination, index + 1, result);
        for (Lesson lesson : currentList) {
            combination[index] = lesson;
            generateCombinations(lists, combination, index + 1, result);
        }
    }

    public List<Lesson[]> generateCombinations(List<List<Lesson>> lists, int desiredUnit) {
        this.desiredUnit = desiredUnit;
        Lesson[] combination = new Lesson[lists.size()];
        List<Lesson[]> result = new ArrayList<>();
        generateCombinations(lists, combination, 0, result);
        System.out.println("result size : " + result.size());
        return result;
    }

    private Boolean isCorrectPlan(List<Lesson> combination) {
        Boolean isCorrectUnitSum = !LessonFilterUtil.hasNotUnit(desiredUnit, combination);
        Boolean hasNotConflict = !LessonFilterUtil.hasConflict(combination);
        return isCorrectUnitSum & hasNotConflict;
    }

}

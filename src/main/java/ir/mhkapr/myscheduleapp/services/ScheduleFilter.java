package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.objects.Lesson;
import ir.mhkapr.myscheduleapp.utils.LessonFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ScheduleFilter implements Callable<List<List<Lesson>>> {

    BigInteger floor;
    BigInteger ceil;
    List<Lesson> lessons;
    int unitSum;

    public ScheduleFilter(BigInteger floor, BigInteger ceil, int unitSum, List<Lesson> lessons) {
        this.floor = floor;
        this.ceil = ceil;
        this.lessons = lessons;
        this.unitSum = unitSum;
    }

    @Override
    public List<List<Lesson>> call() throws Exception {
        List<List<Lesson>> plans = new LinkedList<>();
        while (floor.compareTo(ceil) < 0) {
            BitSet subSetMapper = new BitSet(lessons.size());
            for (int i = 0; i < lessons.size(); i++) {
                if (isOneBit(i, floor)) {
                    subSetMapper.set(i);
                }
            }
            List<Lesson> subSetList = doFilter(subSetMapper);
            if (subSetList != null) plans.add(subSetList);
            floor = floor.add(BigInteger.ONE);
        }
        return plans;
    }

    private Boolean isOneBit(int index, BigInteger number) {
        return number.and(BigInteger.ONE.shiftLeft(index)).compareTo(BigInteger.ZERO) != 0;
    }

    private List<Lesson> doFilter(BitSet subSetMapper) {
        List<Lesson> subSetList = IntStream.range(0, lessons.size() - 1).parallel()
                .filter(subSetMapper::get).mapToObj(lessons::get).toList();

        return !(LessonFilterUtil.hasConflict(subSetList) ||
                LessonFilterUtil.hasManyReligiousLessons(subSetList) ||
                LessonFilterUtil.existsRepetition(subSetList) ||
                LessonFilterUtil.hasNotUnit(5, subSetList)) ? subSetList : null;
    }
}

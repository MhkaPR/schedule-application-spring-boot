package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetService {

    private final List<Lesson> lessonList;

    private List<Integer> getIndexesOfOnes(List<Boolean> bitMapper) {
        List<Integer> tempArr = new ArrayList<>();
        for (int i = 0; i < bitMapper.size(); i++) {
            if (bitMapper.get(i)) {
                tempArr.add(i);
            }
        }
        return tempArr;
    }

    private Boolean isValidOnesCount(Integer onesCount, List<Boolean> bitMapper) {
        return bitMapper.stream().filter(x -> x).mapToInt(x -> 1).count() == onesCount;
    }

    public List<List<Integer>> calculateByRange(Integer min, Integer max) {
        List<List<Integer>> resultArray = new LinkedList<>();
        List<Boolean> bitMapper = new LinkedList<>();
        int temp = 0;
        while (temp < lessonList.size()) {
            bitMapper.add(false);
            temp++;
        }
        for (int i = min; i <= max; i++) {
            while (!isValidOnesCount(lessonList.size(), bitMapper)) {
                if (isValidOnesCount(i, bitMapper)) {
                    resultArray.add(getIndexesOfOnes(bitMapper));
                }
                bitMapper = plusBitMapper(bitMapper);
            }
        }
        return resultArray;
    }

    private List<Boolean> plusBitMapper(List<Boolean> bitMapper) {
        int i = bitMapper.size() - 1;
        while (i >= 0 && bitMapper.get(i)) {
            bitMapper.set(i, false);
            i--;
        }
        if (i >= 0) {
            bitMapper.set(i, true);
        }
        return bitMapper;
    }
}

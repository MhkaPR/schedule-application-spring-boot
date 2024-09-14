package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.DTOs.SubmitDataResponse;
import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private List<List<Lesson>> powerSet(List<Lesson> lessons, Integer unit)
            throws ExecutionException, InterruptedException {
        // Get the number of available cores
        int cores = Runtime.getRuntime().availableProcessors();

        // Create an ExecutorService with a thread pool of size equal to the number of cores
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        BigInteger sizeOfSubSets = BigInteger.TWO.pow(lessons.size());

        List<Future<List<List<Lesson>>>> futures = new LinkedList<>();

        // Submit tasks to the executor
        for (int i = 0; i < cores; i++) {
            BigInteger floor = sizeOfSubSets.divide(BigInteger.valueOf(cores)).multiply(BigInteger.valueOf(i));
            BigInteger ceil = sizeOfSubSets.divide(BigInteger.valueOf(cores)).multiply(BigInteger.valueOf(i + 1));
            if (ceil.compareTo(sizeOfSubSets) > 0) ceil = sizeOfSubSets.add(BigInteger.ONE);
            ScheduleFilter scheduleFilter = new ScheduleFilter(floor, ceil, unit, lessons);
            futures.add(executor.submit(scheduleFilter));
        }
        List<List<Lesson>> allSubSets = new LinkedList<>();

        for (Future<List<List<Lesson>>> future : futures) {
            List<List<Lesson>> result = future.get();
            allSubSets.addAll(result);

        }
        executor.shutdown();

        return allSubSets;

    }

    public SubmitDataResponse buildSchedules(Integer unit, List<Lesson> lessonList)
            throws ExecutionException, InterruptedException {
        return SubmitDataResponse.builder().schedules(powerSet(lessonList, unit)).build();
    }

}

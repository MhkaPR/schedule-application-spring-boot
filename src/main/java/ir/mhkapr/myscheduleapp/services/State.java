package ir.mhkapr.myscheduleapp.services;

import ir.mhkapr.myscheduleapp.objects.Lesson;

import java.util.List;

public class State {
    int index;
    int sum;
    List<Lesson> subset;

    State(int index, int sum, List<Lesson> subset) {
        this.index = index;
        this.sum = sum;
        this.subset = subset;
    }
}

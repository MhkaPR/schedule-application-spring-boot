package ir.mhkapr.myscheduleapp.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private String name;
    private String TeacherName;
    private Integer unit;
    private Integer day1;
    private Integer hour1;
    private Integer eachOtherWeek1;
    private Integer day2;
    private Integer hour2;
    private Integer eachOtherWeek2;
    private Boolean isReligious;
    private Boolean hasLab;
    private String labName;
    private String labTeacherName;
    private Integer labUnit;
    private Integer labDay;
    private Integer labHour;
}

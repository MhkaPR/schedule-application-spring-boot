package ir.mhkapr.myscheduleapp.DTOs;

import ir.mhkapr.myscheduleapp.objects.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDataResponse {
    private List<List<Lesson>> schedules;
}

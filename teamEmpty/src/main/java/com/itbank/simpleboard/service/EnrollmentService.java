package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    
    // 수강 취소
    @Transactional
    public void cancel(Long studentIdx, Long lectureIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        lecture.setCurrentCount(lecture.getCurrentCount()-1);

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentAndLecture(student, lecture);

        if (optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollmentRepository.delete(enrollment);
        }

    }

    @Transactional
    public Enrollment save(Long studentIdx, Long lectureIdx) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        List<Enrollment> enrollmentList = enrollmentRepository.findAllByStudent(student);

        Map<String, List<LocalTime[]>> map = new HashMap<>();
        for (Enrollment e : enrollmentList) {
            String day = e.getLecture().getDay();
            String[] days = day.split(",");
            String start = e.getLecture().getStart();
            String[] starts = start.split(",");
            String end = e.getLecture().getEnd();
            String[] ends = end.split(",");

            for (int i = 0; i < days.length; i++) {
                LocalTime startTime = LocalTime.parse(starts[i], timeFormatter);
                LocalTime endTime = LocalTime.parse(ends[i], timeFormatter);
                LocalTime[] timeRange = {startTime, endTime};

                if (map.containsKey(days[i])) {
                    map.get(days[i]).add(timeRange);
                } else {
                    List<LocalTime[]> times = new ArrayList<>();
                    times.add(timeRange);
                    map.put(days[i], times);
                }
            }
        }

        String[] addDays = lecture.getDay().split(",");
        String[] addStarts = lecture.getStart().split(",");
        String[] addEnds = lecture.getEnd().split(",");

        for (int i = 0; i < addDays.length; i++) {
            LocalTime addStartTime = LocalTime.parse(addStarts[i], timeFormatter);
            LocalTime addEndTime = LocalTime.parse(addEnds[i], timeFormatter);

            if (map.containsKey(addDays[i])) {
                // 반복돌리면서 "ex) "
                for (LocalTime[] timeRange : map.get(addDays[i])) {
                    LocalTime startTime = timeRange[0];
                    LocalTime endTime = timeRange[1];

                    if ((addStartTime.isAfter(startTime) && addStartTime.isBefore(endTime)) ||
                            (addEndTime.isAfter(startTime) && addEndTime.isBefore(endTime)) ||
                            (addStartTime.equals(startTime) && addEndTime.equals(endTime))) {
                        return null;
                    }
                }
            }
        }

        Enrollment enrollment = null;
        if (lecture.getCurrentCount() < lecture.getMaxCount()) {
            lecture.setCurrentCount(lecture.getCurrentCount() + 1);
            enrollment = new Enrollment(student, lecture);
        } else {
            return null;
        }

        return enrollmentRepository.save(enrollment);
    }
    
    public List<Enrollment> findByStudent(Long studentIdx) {
        // 일단 페이징 안됨
        Student student = studentRepository.findById(studentIdx).get();
        List<Enrollment> enrollmentList = enrollmentRepository.findAllByStudent(student);
        return enrollmentList;
    }
}

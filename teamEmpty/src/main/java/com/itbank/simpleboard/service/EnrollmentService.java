package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.EnrollmentDto;
import com.itbank.simpleboard.dto.StudentDto;
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
    
    // 수강 취소
    @Transactional
    public Integer cancel(Long studentIdx, Long lectureIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        lecture.setCurrentCount(lecture.getCurrentCount()-1);

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentAndLecture(student, lecture);

        if (optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollmentRepository.delete(enrollment);
            return 1;
        }else{
            return 0;
        }

    }

    @Transactional
    public Enrollment save(Long studentIdx, Long lectureIdx) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        Student student = studentRepository.findById(studentIdx).orElseThrow(() -> new IllegalArgumentException("Invalid studentIdx: " + studentIdx));
        Lecture lecture = lectureRepository.findById(lectureIdx).orElseThrow(() -> new IllegalArgumentException("Invalid lectureIdx: " + lectureIdx));

        Map<String, List<LocalTime[]>> schedule = getStudentSchedule(timeFormatter, student);

        if(checkScheduleConflict(timeFormatter, lecture, schedule)){
            // 실패일때 그냥 null 반환하면 controller가 처리해줌
            return null;
        }

        if (lecture.getCurrentCount() >= lecture.getMaxCount()) {
            // 실패일때 그냥 null 반환하면 controller가 처리해줌
            return null;
        }

        lecture.setCurrentCount(lecture.getCurrentCount() + 1);
        Enrollment enrollment = new Enrollment(student, lecture);

        return enrollmentRepository.save(enrollment);
    }

    private Map<String, List<LocalTime[]>> getStudentSchedule(DateTimeFormatter timeFormatter, Student student) {
        List<Enrollment> enrollmentList = enrollmentRepository.findByStudent(student);

        Map<String, List<LocalTime[]>> schedule = new HashMap<>();
        for (Enrollment e : enrollmentList) {
            String[] days = e.getLecture().getDay().split(",");
            String[] starts = e.getLecture().getStart().split(",");
            String[] ends = e.getLecture().getEnd().split(",");

            for (int i = 0; i < days.length; i++) {
                LocalTime[] timeRange = parseTimeRange(timeFormatter, starts[i], ends[i]);

                schedule.computeIfAbsent(days[i], k -> new ArrayList<>()).add(timeRange);
            }
        }

        return schedule;
    }

    private LocalTime[] parseTimeRange(DateTimeFormatter timeFormatter, String start, String end) {
        LocalTime startTime = LocalTime.parse(start, timeFormatter);
        LocalTime endTime = LocalTime.parse(end, timeFormatter);

        return new LocalTime[]{startTime, endTime};
    }

    private boolean checkScheduleConflict(DateTimeFormatter timeFormatter, Lecture lecture, Map<String, List<LocalTime[]>> schedule) {
        String[] days = lecture.getDay().split(",");
        String[] starts = lecture.getStart().split(",");
        String[] ends = lecture.getEnd().split(",");

        for (int i = 0; i < days.length; i++) {
            LocalTime startTime = LocalTime.parse(starts[i], timeFormatter);
            LocalTime endTime = LocalTime.parse(ends[i], timeFormatter);

            if (schedule.containsKey(days[i])) {
                for (LocalTime[] timeRange : schedule.get(days[i])) {
                    if ((startTime.isBefore(timeRange[1]) && endTime.isAfter(timeRange[0]))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public List<EnrollmentDto> findByStudentAll(Long studentIdx) {
        // 일단 페이징 안됨
        Student student = studentRepository.findById(studentIdx).get();
        // 해당 학생이 강의평가를 하지 않은 수강신청내역이 List로 나와야한다.
        List<EnrollmentDto> findedEnrollments = enrollmentRepository.findByStudentAll(student);

        return findedEnrollments;
    }

    public List<Enrollment> findByStudent(Long studentIdx){
        Student student = studentRepository.findById(studentIdx).orElse(null);
        return enrollmentRepository.findByStudent(student);
    }
}

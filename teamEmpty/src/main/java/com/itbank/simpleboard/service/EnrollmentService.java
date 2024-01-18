package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.UserRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        List<Enrollment> enrollmentList = enrollmentRepository.findAllByStudent(student);

        Map<String, String> map = new HashMap<>();
        // enrollmentList는 이 사람의 수강신청리스트이다.
        for(Enrollment e : enrollmentList) {
            String day = e.getLecture().getDay();
            String[] days = day.split(",");
            String start = e.getLecture().getStart();
            String[] starts = start.split(",");
            String end = e.getLecture().getEnd();
            String[] ends = end.split(",");

            // 시간비교, 분 비교
            for(int i=0;i<days.length;i++) {
                if (map.containsKey(days[i])) {
                    map.put(days[i], map.get(days[i]) + "," + starts[i] + "~" + ends[i]);
                } else {
                    map.put(days[i], starts[i] + "~" + ends[i]);
                }
            }
        }
        System.err.println("map : " + map);
        Map<String, String> addLectureMap = new HashMap<>();
        String[] addDays = lecture.getDay().split(",");
        String[] addStarts = lecture.getStart().split(",");
        String[] addEnds = lecture.getEnd().split(",");


        // 새로운 강의의 각 요일별 수업시간을 검사합니다.
        for (int i = 0; i < addDays.length; i++) {
            String addDay = addDays[i];
            String addStart = addStarts[i];
            String addEnd = addEnds[i];

            // 해당 요일에 이미 수강신청한 강의가 있는지 확인합니다.
            if (map.containsKey(addDay)) {
                String[] times = map.get(addDay).split(",");
                for (String time : times) {
                    String[] timeRange = time.split("~");
                    String start = timeRange[0];
                    String end = timeRange[1];

                    // 새로운 강의의 시간이 기존의 수업시간과 겹치는지 확인합니다.
                    if ((addStart.compareTo(start) >= 0 && addStart.compareTo(end) <= 0) || (addEnd.compareTo(start) >= 0 && addEnd.compareTo(end) <= 0)) {
                        // 수업시간이 겹치므로 수강신청이 불가능합니다.
                        return null;
                    }
                }
            }
        }

        Enrollment enrollment = null;
        if(lecture.getCurrentCount() < lecture.getMaxCount()){
            lecture.setCurrentCount(lecture.getCurrentCount()+1);
            enrollment = new Enrollment(student, lecture);
        }else{
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

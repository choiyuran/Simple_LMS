package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.repository.AcademicCalendarRepository;
import com.itbank.simpleboard.repository.LectureRoomRepository;
import com.itbank.simpleboard.repository.manager.CollegeRepository;
import com.itbank.simpleboard.repository.manager.MajorRepository;

import com.itbank.simpleboard.repository.manager.ManagerRepository;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;
    private final AcademicCalendarRepository academicCalendarRepository;
    private final ProfessorRepository professorRepository;
    private final LectureRoomRepository lectureRoomRepository;
    private final LectureRepository lectureRepository;

    public List<ManagerDTO> findAllManager() {
        List<Manager> managerList = managerRepository.findAll();
        List<ManagerDTO> managerDTOList = new ArrayList<>();

        for(Manager m : managerList){
            ManagerDTO dto = new ManagerDTO();
            dto.setManagerImg(m.getManager_img());
            dto.setManagerId(m.getUser().getUser_id());
            dto.setManagerName(m.getUser().getUser_name());
            dto.setManagerPnum(m.getUser().getPnum());
            dto.setManagerEmail(m.getUser().getEmail());
            dto.setManagerHireDate(m.getHireDate());
            managerDTOList.add(dto);
        }

        return managerDTOList;
    }

    public List<ManagerDTO> searchManager(String searchType, String searchValue) {
        List<ManagerDTO> searchManagerList = managerRepository.findBySearchType(searchType,searchValue);
        return searchManagerList;
    }

    public List<College> selectAllCollege() {
        return collegeRepository.findAll();
    }

    @Transactional
    public Major addMajor(MajorDto major) {
        College college = collegeRepository.findById(major.getCollege_idx()).get();
        Major major1 = new Major(major.getName(),major.getTuition(),college);
        return majorRepository.save(major1);
    }

    public List<Major> selectAllMajor() {
        return  majorRepository.findByAbolition(YesOrNo.N);
    }

    public Major selectOne(Long idx) {
        return majorRepository.findById(idx).get();
    }

    @Transactional
    public Major majorUpdate(MajorDto param) {
        Major major = majorRepository.findById(param.getIdx()).get();
        major.setName(param.getName());
        major.setTuition(param.getTuition());
        return major;
    }

    @Transactional
    public Major majorDel(Long idx) {
        Major major = majorRepository.findById(idx).get();
        major.setAbolition(YesOrNo.Y);
        return major;
    }

    public List<AcademicCalendar> findAll() {
        return academicCalendarRepository.findAll();
    }

    @Transactional
    public Lecture addLecture(RegisterlectureDto param) {
        StringBuilder day = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();

        System.err.println("param : "+ param.toString());

        for(int i = 0; i < param.getDay().length; i++) {
            day.append(param.getDay()[i]);
            start.append(param.getStart()[i]);
            end.append(param.getEnd()[i]);
            if(i != param.getDay().length - 1) {
                day.append(",");
                start.append(",");
                end.append(",");
            }
        }
        Professor professor = professorRepository.findById(param.getProfessor_idx()).get();
        Major major = majorRepository.findById(param.getMajor_idx()).get();
        LectureRoom lectureRoom = lectureRoomRepository.findById(param.getLectureRoom_idx()).get();

       Lecture lecture = new Lecture(
               param.getName(),
               param.getIntro(),
               param.getCredit(),
               param.getType(),
               professor,
               param.getMax_count(),
               param.getCurrent_count(),
               param.getSemester(),
               param.getGrade(),
               major,
               lectureRoom
       );
        lecture.setDay(day.toString());
        lecture.setStart(start.toString());
        lecture.setEnd(end.toString());
        return lectureRepository.save(lecture);
    }

    public Lecture selectOneLecture(Long idx) {
        return lectureRepository.findById(idx).get();
    }

    @Transactional
    public Lecture updateLecture(RegisterlectureDto param) {
        log.info("service : " + param.toString());
        Lecture lecture = lectureRepository.findById(param.getIdx()).get();

        StringBuilder day = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();

        for(int i = 0; i < param.getDay().length; i++) {
            day.append(param.getDay()[i]);
            if (i != param.getDay().length - 1) {
                day.append(",");
            }
        }

        for(int i = 0; i < param.getStart().length; i++) {
            if (param.getStart()[i] != null) {
                start.append(param.getStart()[i]);
                if(param.getEnd()[i] != null) {
                    end.append(param.getEnd()[i]);
                }
                if (i != param.getStart().length - 1) {
                    start.append(",");
                    end.append(",");
                }
            }
        }

        Professor professor = professorRepository.findById(param.getProfessor_idx())
                .orElseThrow(() -> new NoSuchElementException("Professor with id " + param.getProfessor_idx() + " not found."));
        Major major = majorRepository.findById(param.getMajor_idx())
                .orElseThrow(() -> new NoSuchElementException("Major with id " + param.getMajor_idx() + " not found."));
        LectureRoom lectureRoom = lectureRoomRepository.findById(param.getLectureRoom_idx())
                .orElseThrow(() -> new NoSuchElementException("LectureRoom with id " + param.getLectureRoom_idx() + " not found."));

        lecture.setName(param.getName());
        lecture.setIntro(param.getIntro());
        lecture.setCredit(param.getCredit());
        lecture.setType(param.getType());
        lecture.setProfessor(professor);
        lecture.setMaxCount(param.getMax_count());
        lecture.setCurrentCount(param.getCurrent_count());
        lecture.setSemester(param.getSemester());
        lecture.setGrade(param.getGrade());
        lecture.setMajor(major);
        lecture.setLectureRoom(lectureRoom);
        lecture.setDay(day.toString());
        lecture.setStart(start.toString());
        lecture.setEnd(end.toString());

        return lecture;
    }

    @Transactional
    public Lecture delLecture(Long idx) {
        Lecture lecture = lectureRepository.findById(idx).get();
        lecture.setAbolition(YesOrNo.Y);
        return lecture;
    }


    public List<Major> searchByCollege(Long collegeIdx) {
        College college = collegeRepository.findById(collegeIdx).get();
        return majorRepository.findByCollege(college);
    }

    public List<Major> searchByCollegeAndMajor(Long collegeIdx, String majorName) {
        College college = collegeRepository.findById(collegeIdx).get();
        return majorRepository.searchByCollegeAndNameContaining(college, majorName);
    }

    public List<Major> searchByMajor(String majorName) {
        return majorRepository.findByNameContaining(majorName);
    }

    @Transactional
    public Manager addManager(UserFormDTO dto) {
        System.err.println("userFormDTO : "+ dto.toString());
        String salt = "";
        String pw = dto.getBackSecurity() /*security.substring(security.length()-7)*/;
        String userName = dto.getFirstName()+dto.getLastName();
        String security = dto.getFrontSecurity() + "-"+ dto.getBackSecurity();
        String manager_img = dto.getImageFile().toString();
        Date hireDate = new java.sql.Date(dto.getHireDate().getTime());
        User user = new User(
                pw,
                salt,
                userName,
                security,
                dto.getAddress(),
                dto.getPnum(),
                dto.getEmail(),
                User_role.교직원
        );
        userRepository.save(user);
        Manager manager = new Manager(
                manager_img,
                user,
                hireDate
        );
        return managerRepository.save(manager);
    }

    @Transactional
    public Professor addProfessor(UserFormDTO dto) {
        log.info("addProfessor service");
        System.err.println("userFormDTO : "+ dto.toString());
        String salt = "";
        String pw = dto.getBackSecurity() /*security.substring(security.length()-7)*/;
        String userName = dto.getFirstName()+dto.getLastName();
        String security = dto.getFrontSecurity() + "-"+ dto.getBackSecurity();
        String professor_img = dto.getImageFile().toString();
        Date hireDate = new java.sql.Date(dto.getHireDate().getTime());
        User user = new User(
                pw,
                salt,
                userName,
                security,
                dto.getAddress(),
                dto.getPnum(),
                dto.getEmail(),
                User_role.교수
        );
        userRepository.save(user);
        Optional<Major> majorOptional = majorRepository.findById(dto.getMajor());
        Major major = majorOptional.orElse(null); // 값이 존재하지 않을 경우에는 null을 반환하도록 처리


        Professor professor = new Professor(
                professor_img,
                user,
                major,
                hireDate
        );

        return professorRepository.save(professor);
    }
}


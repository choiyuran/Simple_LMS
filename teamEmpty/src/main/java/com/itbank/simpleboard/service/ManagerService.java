package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.GlobalVariable;
import com.itbank.simpleboard.component.HashComponent;
import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.repository.AcademicCalendarRepository;
import com.itbank.simpleboard.repository.LectureRoomRepository;
import com.itbank.simpleboard.repository.PaymentsRepository;
import com.itbank.simpleboard.repository.ScholarshipRepository;
import com.itbank.simpleboard.repository.manager.CollegeRepository;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import com.itbank.simpleboard.repository.manager.ManagerRepository;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.student.SituationRecordRepository;
import com.itbank.simpleboard.repository.student.SituationRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final FileService fileService;
    private final StudentRepository studentRepository;
    private final HashComponent hashComponent;
    private final SituationRepository situationRepository;
    private final SituationRecordRepository situationRecordRepository;
    private final PaymentsRepository paymentsRepository;
    private final GlobalVariable globalVariable;
    private final ScholarshipRepository scholarshipRepository;

    public Page<ManagerDTO> findAllManager(Pageable pageable) {
        Page<Manager> managerList = managerRepository.findAll(pageable);
        List<ManagerDTO> managerDTOList = new ArrayList<>();

        for (Manager m : managerList) {
            ManagerDTO dto = new ManagerDTO();
            dto.setIdx(m.getIdx());
            dto.setImg(m.getManager_img());
            dto.setManagerId(m.getUser().getUser_id());
            dto.setManagerName(m.getUser().getUser_name());
            dto.setManagerPnum(m.getUser().getPnum());
            dto.setManagerEmail(m.getUser().getEmail());
            dto.setManagerHireDate(m.getHireDate());
            dto.setAddress(m.getUser().getAddress());
            dto.setLeaveDate(m.getLeaveDate());
            managerDTOList.add(dto);
        }

        return new PageImpl<>(managerDTOList, pageable, managerList.getTotalElements());
    }

    public Page<ManagerDTO> searchManager(HashMap<String, Object> map, Pageable pageable) {
        String searchType = (String) map.get("searchType");
        String searchValue = (String) map.get("searchValue");
        String searchValueKey = "";
        if (searchType.equals("name")) {
            if (searchValue != null) {
                for (int i = 0; i < searchValue.length(); i++) {
                    searchValueKey += searchValue.charAt(i);
                    searchValueKey += "%";
                }
            }
        }
        map.put("searchValue", searchValueKey);
        return managerRepository.findBySearchType(map, pageable);
    }

    public List<College> selectAllCollege() {
        return collegeRepository.findAll();
    }

    @Transactional
    public Major addMajor(MajorDto major) {
        College college = collegeRepository.findById(major.getCollege_idx()).get();
        Major major1 = new Major(major.getName(), major.getTuition(), college);
        return majorRepository.save(major1);
    }

    public List<Major> selectAllMajor() {
        return majorRepository.findByAbolition(YesOrNo.N);
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
        LectureRoom lectureRoom = lectureRoomRepository.findById(param.getLectureRoom_idx()).get();

        // 겹치는지 체크
        List<Lecture> lectureList = lectureRepository.findByLectureRoom(lectureRoom);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        Map<String, List<LocalTime[]>> schedule = getLectureRoomSchedule(timeFormatter, lectureRoom);
        if (checkScheduleConflict(timeFormatter, param, schedule)) {
            return null;
        }

        StringBuilder day = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();

        for (int i = 0; i < param.getDay().length; i++) {
            day.append(param.getDay()[i]);
            start.append(param.getStart()[i]);
            end.append(param.getEnd()[i]);
            if (i != param.getDay().length - 1) {
                day.append(",");
                start.append(",");
                end.append(",");
            }
        }
        Professor professor = professorRepository.findById(param.getProfessor_idx()).get();
        Major major = majorRepository.findById(param.getMajor_idx()).get();

        Lecture lecture = new Lecture(
                param.getName(),
                param.getIntro(),
                param.getCredit(),
                param.getType(),
                professor,
                param.getMax_count(),
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


    private LocalTime[] parseTimeRange(DateTimeFormatter timeFormatter, String start, String end) {
        LocalTime startTime = LocalTime.parse(start, timeFormatter);
        LocalTime endTime = LocalTime.parse(end, timeFormatter);

        return new LocalTime[]{startTime, endTime};
    }

    private Map<String, List<LocalTime[]>> getLectureRoomSchedule(DateTimeFormatter timeFormatter, LectureRoom lectureRoom) {
        List<Lecture> lectureList = lectureRepository.findByLectureRoom(lectureRoom);
        Map<String, List<LocalTime[]>> schedule = new HashMap<>();
        for (Lecture l : lectureList) {
            String[] days = l.getDay().split(",");
            String[] starts = l.getStart().split(",");
            String[] ends = l.getEnd().split(",");

            for (int i = 0; i < days.length; i++) {
                LocalTime[] timeRange = parseTimeRange(timeFormatter, starts[i], ends[i]);
                schedule.computeIfAbsent(days[i], k -> new ArrayList<>()).add(timeRange);
            }
        }
        return schedule;
    }

    private Map<String, List<LocalTime[]>> getLectureRoomScheduleUpdate(DateTimeFormatter timeFormatter, LectureRoom lectureRoom, Long lectureIdx) {
        List<Lecture> lectureList = lectureRepository.findByLectureRoom(lectureRoom).stream()
                .filter(l -> !l.getIdx().equals(lectureIdx)) // lectureIdx와 일치하지 않는 객체만 선택
                .collect(Collectors.toList());

        Map<String, List<LocalTime[]>> schedule = new HashMap<>();
        for (Lecture l : lectureList) {
            String[] days = l.getDay().split(",");
            String[] starts = l.getStart().split(",");
            String[] ends = l.getEnd().split(",");

            for (int i = 0; i < days.length; i++) {
                LocalTime[] timeRange = parseTimeRange(timeFormatter, starts[i], ends[i]);
                schedule.computeIfAbsent(days[i], k -> new ArrayList<>()).add(timeRange);
            }
        }
        return schedule;
    }

    private boolean checkScheduleConflict(DateTimeFormatter timeFormatter, RegisterlectureDto param, Map<String, List<LocalTime[]>> schedule) {
        for (int i = 0; i < param.getDay().length; i++) {
            LocalTime startTime = LocalTime.parse(param.getStart()[i], timeFormatter);
            LocalTime endTime = LocalTime.parse(param.getEnd()[i], timeFormatter);
            if (schedule.containsKey(param.getDay()[i])) {
                for (LocalTime[] timeRange : schedule.get(param.getDay()[i])) {
                    // startTime이 timeRange[1] 이전이 아니거나(즉, 동일하거나 늦음),
                    // endTime이 timeRange[0] 이후가 아닌 경우(즉, 동일하거나 이르면) 겹치는 것으로 간주
                    if (!(startTime.isAfter(timeRange[1]) || endTime.isBefore(timeRange[0]))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkScheduleConflictUpdate(DateTimeFormatter timeFormatter, String[] day, String start, String end, Map<String, List<LocalTime[]>> schedule) {
        for (int i = 0; i < day.length; i++) {
            LocalTime startTime = LocalTime.parse(start.split(",")[i], timeFormatter);
            LocalTime endTime = LocalTime.parse(end.split(",")[i], timeFormatter);
            if (schedule.containsKey(day[i])) {
                for (LocalTime[] timeRange : schedule.get(day[i])) {
                    if (!(startTime.isAfter(timeRange[1]) || endTime.isBefore(timeRange[0]))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Lecture selectOneLecture(Long idx) {
        return lectureRepository.findById(idx).get();
    }

    @Transactional
    public Lecture updateLecture(RegisterlectureDto param, Long lectureIdx) {
        StringBuilder day = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();

        for (int i = 0; i < param.getDay().length; i++) {
            day.append(param.getDay()[i]);
            if (i != param.getDay().length - 1) {
                day.append(",");
            }
        }
        for (int i = 0; i < param.getStart().length; i++) {
            if (param.getStart()[i] != null) {
                start.append(param.getStart()[i]);
                if (param.getEnd()[i] != null) {
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
        // 겹치는지 체크
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        Map<String, List<LocalTime[]>> schedule = getLectureRoomScheduleUpdate(timeFormatter, lectureRoom, lectureIdx);
        log.info("수정 schedule : " + schedule);
        if (checkScheduleConflictUpdate(timeFormatter, param.getDay(), start.toString(), end.toString(), schedule)) {
            return null;
        }

        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        lecture.setName(param.getName());
        lecture.setIntro(param.getIntro());
        lecture.setCredit(param.getCredit());
        lecture.setType(param.getType());
        lecture.setProfessor(professor);
        lecture.setMaxCount(param.getMax_count());
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


    public Page<Major> searchByCollege(Long collegeIdx, Pageable pageable) {
        College college = collegeRepository.findById(collegeIdx).get();
        return majorRepository.findByCollege(college, pageable);
    }

    public Page<Major> searchByCollegeAndMajor(Long collegeIdx, String majorName, Pageable pageable) {
        College college = collegeRepository.findById(collegeIdx).get();
        return majorRepository.searchByCollegeAndNameContaining(college, majorName, pageable);
    }

    public List<Major> searchByMajor(String majorName) {
        return majorRepository.findByNameContaining(majorName);
    }

    @Transactional
    public Professor addProfessor(UserFormDTO dto, MultipartFile imageFile) {
        log.info("addProfessor service" + dto.toString());

        String salt = hashComponent.getRandomSalt();
        String source = dto.getBackSecurity();
        String pw = hashComponent.getHash(source, salt);
        String userName = dto.getFirstName() + dto.getLastName();
        String security = dto.getFrontSecurity() + "-" + dto.getBackSecurity();
        // 새로운 파일 이름 생성 (사용자 이름과 주민등록번호로 조합)
        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = userName + "_" + dto.getFrontSecurity() + extension;
        String professor_img = fileService.uploadIdPhoto(imageFile, "idPhoto_professor", newFileName);
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

    @Transactional
    public Manager addManager(UserFormDTO dto, MultipartFile imageFile) {

        String salt = hashComponent.getRandomSalt();
        String source = dto.getBackSecurity();
        String pw = hashComponent.getHash(source, salt);
        String userName = dto.getFirstName() + dto.getLastName();
        String security = dto.getFrontSecurity() + "-" + dto.getBackSecurity();

        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = userName + "_" + dto.getFrontSecurity() + extension;
        String manager_img = fileService.uploadIdPhoto(imageFile, "idPhoto_manager", newFileName);

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
    public String verificationStudentDTOList(List<StudentFormDTO> studentList) {
        log.info("verificationStudentDTOList service");
        int index = 1;
        StringBuilder notFoundStudents = new StringBuilder();// 교수정보를 찾을 수 없는 학생들의 번호를 담을 StringBuilder
        for (StudentFormDTO dto : studentList) {

//            Long idx = extractedIdx(dto.getMajor());

            Optional<Major> major = majorRepository.findById(extractedIdx(dto.getMajor()));
            if (!major.get().getName().equals(extractedName(dto.getMajor()))) {
                notFoundStudents.append(index).append(", ");
                index++;
                continue;
            }

            List<Professor> professors = professorRepository.findAllByMajor(major.get());

            boolean found = false;
            for (Professor p : professors) {
                if (p.getProfessor_idx().equals(extractedIdx(dto.getProfessor()))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                notFoundStudents.append(index).append(", ");
            }
            log.info("index" + index);
            index++;
        }

        String msg;
        if (notFoundStudents.length() > 0) {
            msg = notFoundStudents.substring(0, notFoundStudents.length() - 2) + "번의 학생의 학과 혹은 교수정보를 찾을 수 없습니다.";
        } else {

            msg = "성공";
        }
        return msg;

    }


    @Transactional
    public String addStudentList(List<StudentFormDTO> studentList) {
        log.info("addStudentList service");
        int index = 0;

        for (StudentFormDTO dto : studentList) {
            String salt = hashComponent.getRandomSalt();
            String source = dto.getSecurity().substring(dto.getSecurity().length() - 7);
            String pw = hashComponent.getHash(source, salt);

            Optional<Major> major = majorRepository.findById(extractedIdx(dto.getMajor()));
            Optional<Professor> professor = professorRepository.findById(extractedIdx(dto.getProfessor()));

            if (major.isPresent() && professor.isPresent()) {
                User user = new User(
                        pw,
                        salt,
                        dto.getName(),
                        dto.getSecurity(),
                        dto.getAddress(),
                        dto.getPnum(),
                        dto.getEmail(),
                        User_role.학생
                );
                userRepository.save(user);

                log.info("dto.getStudent_grade() : {} ", dto.getStudent_grade());
                log.info("user : {} ", user);
                log.info("professor.get() : {} ", professor.get());
                log.info("major.get() : {} ", major.get().getIdx());
                log.info("dto.getEntranceDate() : {} ", dto.getEntranceDate().toLocalDate().getYear());
                log.info("major.get().getIdx() : {} ", major.get().getIdx());
                Integer lastStudentNum = studentRepository.findByEntranceDateAndMajorIdx(dto.getEntranceDate().toLocalDate().getYear() % 100, major.get().getIdx());
                log.info("lastStudentNum : {} ", lastStudentNum);
                Student s = new Student(
                        dto.getStudent_grade(),
                        user,
                        professor.get(),
                        major.get(),
                        dto.getEntranceDate(),
                        major.get().getIdx(),
                        lastStudentNum
                );

                Situation situation = new Situation(
                        s,
                        Status_type.재학
                );
                SituationRecord situationRecord = new SituationRecord(
                        Status_type.입학,
                        s
                );

                Student student = studentRepository.save(s);
                situationRepository.save(situation);
                situationRecordRepository.save(situationRecord);
                Payments payments = new Payments(student, globalVariable.getGlobalSememster());
                Payments savedPayments = paymentsRepository.save(payments);

                log.info("savedPayments : " + savedPayments);

                log.info("user.getUser_id : {} ", user.getUser_id());
                user.setUser_id(String.valueOf(s.getStudent_num()));

                log.info("user.setUser_id : {} ", user.getUser_id());
                log.info("s.getUser().getUser_id()" + s.getUser().getUser_id());


                log.info("index" + index);
                index++;
            }

        }

        return index + "명의 학생 등록에 성공하였습니다.";
    }


    public List<MajorDto> getMajorList(String collegeName) {
        List<Major> List = majorRepository.findByCollegeName(collegeName);
        List<MajorDto> majorList = new ArrayList<>();
        long idx = 1;

        if (!List.isEmpty()) {
            for (Major m : List) {
                MajorDto dto = new MajorDto(
                        m.getName(),
                        m.getCollege().getIdx(),
                        m.getCollege().getName()
                );
                dto.setIdx(idx++);
                majorList.add(dto);
                log.info("dto.getName() : " + dto.getName());
                log.info("dto : " + dto);
            }
        }
        log.info("majorList.get(0).getName() : " + majorList.get(0).getName());
        log.info("majorList. : " + majorList);
        return majorList;
    }

    public Page<ProfessorListDto> searchByMajorAndProfessorAndLeave(HashMap<String, Object> map, Pageable pageable) {
        String name = (String) map.get("name");
        String namekey = "";
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                namekey += name.charAt(i);
                namekey += "%";
            }
        }
        map.put("name", namekey);
        return professorRepository.searchByMajorAndProfessorAndLeave(map, pageable);
    }


    public ProfessorListDto selectOneProfessor(Long idx) {
        return professorRepository.selectOneProfessor(idx);
    }

    @Transactional
    public int updateProfessorByManager(ProfessorListDto param) {
        int result = 0;

        java.util.Date hire = param.getHireDate();
        Date hireDate = new Date(hire.getTime());

        Optional<Professor> professorOptional = professorRepository.findById(param.getIdx());
        Optional<Major> majorOptional = majorRepository.findById(param.getMajor_idx());
        if (professorOptional.isPresent() && majorOptional.isPresent()) {
            Professor professor = professorOptional.get();
            Major major = majorOptional.get();
            Optional<User> userOptional = userRepository.findById(professor.getUser().getIdx());
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                professor.setMajor(major);
                professor.setHireDate(hireDate);
                if (param.getLeaveDate() != null) {
                    java.util.Date leave = param.getLeaveDate();
                    Date leaveDate = new Date(leave.getTime());
                    professor.setLeave(YesOrNo.Y);
                    professor.setLeaveDate(leaveDate);
                }
                user.setUser_name(param.getName());
                user.setUser_id(param.getEmail());
                user.setEmail(param.getEmail());
                user.setAddress(param.getAddress());
                user.setPnum(param.getPnum());

                if (!param.getProfessorImg().isEmpty()) {
                    fileService.deleteFile(professor.getProfessor_img(), "idPhoto_professor");
                    MultipartFile professorImg = param.getProfessorImg();
                    String extension = professorImg.getOriginalFilename().substring(professorImg.getOriginalFilename().lastIndexOf('.'));
                    String newFileName = user.getUser_name() + "_" + user.getSecurity().split("-")[0] + extension;
                    String idPhotoProfessor = fileService.uploadIdPhoto(professorImg, "idPhoto_professor", newFileName);
                    professor.setProfessor_img(idPhotoProfessor);
                }
                result = 1;
            }
        }

        return result;
    }

    @Transactional
    public Professor professorDel(HashMap<String, Object> map) {
        Long idx = (Long) map.get("idx");
        java.util.Date leaveDate = (java.util.Date) map.get("leaveDate");
        Professor professor = professorRepository.findById(idx).get();
        professor.setLeave(YesOrNo.Y);
        professor.setLeaveDate(new Date(leaveDate.getTime()));
        return professor;
    }

    public Page<StudentListDto> selectAllStudent(HashMap<String, Object> map, Pageable pageable) {
        String name = (String) map.get("name");
        String namekey = "";
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                namekey += name.charAt(i);
                namekey += "%";
            }
        }
        map.put("name", namekey);
        return studentRepository.selectAllStudent(map, pageable);
    }

    public StudentListDto selectOneStudent(Long idx) {
        return studentRepository.selectOndeStudent(idx);
    }

    @Transactional
    public Student studentUpdateByManager(StudentListDto param) {
        Student student = studentRepository.findById(param.getIdx()).get();
        User user = userRepository.findById(student.getUser().getIdx()).get();
        Major major = majorRepository.findById(param.getMajor_idx()).get();
        java.util.Date entrance = param.getEntranceDate();
        Date entranceDate = new Date(entrance.getTime());

        user.setUser_name(param.getName());
        student.setStudent_num(param.getStudent_num());
        user.setUser_id(String.valueOf(param.getStudent_num()));
        student.setMajor(major);
        student.setEnteranceDate(entranceDate);
        user.setAddress(param.getAddress());
        user.setPnum(param.getPnum());
        user.setEmail(param.getEmail());
        return student;
    }

    public ManagerDTO selectOneManager(Long idx) {
        return managerRepository.selectOneManager(idx);
    }

    @Transactional
    public int updateManagerByManager(ManagerDTO param) {
        int result = 0;
        java.util.Date hire = param.getManagerHireDate();
        Date hireDate = new Date(hire.getTime());

        Optional<Manager> managerOptional = managerRepository.findById(param.getIdx());
        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            Optional<User> userOptional = userRepository.findById(manager.getUser().getIdx());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                manager.setHireDate(hireDate);
                if (param.getLeaveDate() != null) {
                    java.util.Date leave = param.getLeaveDate();
                    Date leaveDate = new Date(leave.getTime());
                    manager.setLeave(YesOrNo.Y);
                    manager.setLeaveDate(leaveDate);
                }
                user.setUser_name(param.getManagerName());
                user.setEmail(param.getManagerEmail());
                user.setUser_id(param.getManagerEmail());
                user.setAddress(param.getAddress());
                user.setPnum(param.getManagerPnum());

                if (!param.getManagerImg().isEmpty()) {
                    fileService.deleteFile(manager.getManager_img(), "idPhoto_manager");
                    MultipartFile managerImg = param.getManagerImg();
                    String extension = managerImg.getOriginalFilename().substring(managerImg.getOriginalFilename().lastIndexOf('.'));
                    String newFileName = user.getUser_name() + "_" + user.getSecurity().split("-")[0] + extension;
                    String idPhotoManager = fileService.uploadIdPhoto(managerImg, "idPhoto_manager", newFileName);
                    manager.setManager_img(idPhotoManager);
                }
                result = 1;
            }
        }
        return result;
    }

    @Transactional
    public Manager managerDel(Map<String, Object> map) {
        Long idx = (Long) map.get("idx");
        java.util.Date leaveDate = (java.util.Date) map.get("leaveDate");
        Manager manager = managerRepository.findById(idx).get();
        Date date = new Date(leaveDate.getTime());

        manager.setLeave(YesOrNo.Y);
        manager.setLeaveDate(date);
        return manager;
    }


    public List<CheckTuitionPaymentDto> getCheckTuitionPayment(CheckTutionPaymentConditionDto condition) {
        return managerRepository.findAllCheckTuitionPayments(condition);
    }

    private String extractedName(String str) {
        int end = str.indexOf('(');
        log.info("end : " + end);
        log.info("name : " + str.substring(0, end));
        return str.substring(0, end);
    }

    private static Long extractedIdx(String str) {
        long idx = 0L;
        int start = str.indexOf('(') + 1; // '(' 다음부터 시작
        int end = str.indexOf(')');
        log.info("start : " + start);
        log.info("end : " + end);
        try {
            idx = Long.parseLong(str.substring(start, end));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        log.info("idx : " + idx); // 변수 idx로 수정
        return idx;
    }

    /**
     * 게시글 전체조회
     *
     * @param pageable 페이징 처리
     * @return 게시글 목록 (페이징)
     */
    public Page<Major> majorListPaging(Pageable pageable) {
        return majorRepository.findAll(pageable);
    }

    public Page<Major> selectAllMajorPaging(Pageable pageable) {
        return majorRepository.findByAbolition(pageable, YesOrNo.N);
    }

    public Page<Major> searchByMajorPaging(String majorName, Pageable pageable) {
        return majorRepository.findByNameContaining(majorName, pageable);
    }

    public List<EvaluateFormDto> getEvaluation(Long idx) {
        return managerRepository.viewEvaluation(idx);
    }

    public Map<String, Map<String, Long>> countTotalQ1Q2Q3(List<EvaluateFormDto> evaluation) {
        Map<String, Map<String, Long>> result = new HashMap<>();

        Map<String, Long> q1 = evaluation.stream()
                .flatMap(dto -> Stream.of(dto.getQ1()))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        Map<String, Long> q2 = evaluation.stream()
                .flatMap(dto -> Stream.of(dto.getQ2()))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        Map<String, Long> q3 = evaluation.stream()
                .flatMap(dto -> Stream.of(dto.getQ3()))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        result.put("q1", q1);
        result.put("q2", q2);
        result.put("q3", q3);

        return result;
    }

    @Transactional
    public String lectureEvaluation() {
        List<Lecture> lectureList = lectureRepository.findAll()
                .stream()
                .filter(lecture -> lecture.getAbolition().equals(YesOrNo.N))
                .collect(Collectors.toList());

        String evaluationStatus = null;
        for (Lecture one : lectureList) {
            if (one.getVisible().equals(YesOrNo.Y)) {
                one.setVisible(YesOrNo.N);
                evaluationStatus = "N";
            } else {
                one.setVisible(YesOrNo.Y);
                evaluationStatus = "Y";
            }
        }
        return evaluationStatus;
    }

    public String selectEvaluationStatus() {
        List<Lecture> lectureList = lectureRepository.findAll()
                .stream()
                .filter(lecture -> lecture.getAbolition().equals(YesOrNo.N))
                .collect(Collectors.toList());

        boolean allVisible = lectureList.stream().allMatch(lecture -> lecture.getVisible().equals(YesOrNo.Y));
        boolean noneVisible = lectureList.stream().allMatch(lecture -> lecture.getVisible().equals(YesOrNo.N));

        String evaluationStatus = null;
        if (allVisible) {
            evaluationStatus = "Y";
        } else if (noneVisible) {
            evaluationStatus = "N";
        }

        return evaluationStatus;
    }

    public Page<ScholarshipDto> findAllScholarship(Pageable pageable, ScholarshipDto dto) {
        return scholarshipRepository.findAllScholarship(pageable, dto);
    }

    public List<Scholarship> findAllScholarshipList() {
        return scholarshipRepository.findAll();
    }
}


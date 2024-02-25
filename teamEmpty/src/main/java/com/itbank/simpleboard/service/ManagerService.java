package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.HashComponent;
import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.dto.RegisterlectureDto;
import com.itbank.simpleboard.dto.UserFormDTO;
import com.itbank.simpleboard.component.FileComponent;
import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.repository.AcademicCalendarRepository;
import com.itbank.simpleboard.repository.LectureRoomRepository;
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private final FileComponent fileComponent;
    private final StudentRepository studentRepository;
    private final HashComponent hashComponent;
    private final SituationRepository situationRepository;
    private final SituationRecordRepository situationRecordRepository;

    public Page<ManagerDTO> findAllManager(Pageable pageable) {
        Page<Manager> managerList = managerRepository.findAll(pageable);
        List<ManagerDTO> managerDTOList = new ArrayList<>();

        for(Manager m : managerList){
            ManagerDTO dto = new ManagerDTO();
            dto.setIdx(m.getIdx());
            dto.setManagerImg(m.getManager_img());
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
        Page<ManagerDTO> searchManagerList = managerRepository.findBySearchType(map, pageable);
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
        log.info("addProfessor service"+ dto.toString());
        
        String salt = hashComponent.getRandomSalt();
        String source = dto.getBackSecurity();
        String pw = hashComponent.getHash(source,salt);
        String userName = dto.getFirstName()+dto.getLastName();
        String security = dto.getFrontSecurity() + "-"+ dto.getBackSecurity();
        // 새로운 파일 이름 생성 (사용자 이름과 주민등록번호로 조합)
        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = userName + "_" + dto.getFrontSecurity() + extension;
        String professor_img = fileComponent.uploadIdPhoto(imageFile, "idPhoto_professor",newFileName);
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
        System.err.println("userFormDTO : "+ dto.toString());
        String salt = hashComponent.getRandomSalt();
        String source = dto.getBackSecurity();
        String pw = hashComponent.getHash(source,salt);
        String userName = dto.getFirstName()+dto.getLastName();
        String security = dto.getFrontSecurity() + "-"+ dto.getBackSecurity();
        
        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = userName + "_" + dto.getFrontSecurity() + extension;
        String manager_img = fileComponent.uploadIdPhoto(imageFile, "idPhoto_manager",newFileName);
        System.err.println("manager_img : " + manager_img);
        System.err.println("newFileName : " + newFileName);
//        System.err.println("imageFile : " + dto.getImageFile().getOriginalFilename());
        System.err.println("imageFile : " + imageFile.getOriginalFilename());
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
        StringBuilder notFoundStudents  = new StringBuilder();// 교수정보를 찾을 수 없는 학생들의 번호를 담을 StringBuilder
        for(StudentFormDTO dto : studentList) {
            System.err.println(index + "번 학생 확인 시작");
//            Long idx = extractedIdx(dto.getMajor());

            Optional<Major> major = majorRepository.findById(extractedIdx(dto.getMajor()));
            if(!major.get().getName().equals(extractedName(dto.getMajor()))){
                notFoundStudents.append(index).append(", ");
                index++;
                continue;
            }
            System.err.println(major.get().getName()+ "major");
            List<Professor> professors = professorRepository.findAllByMajor(major.get());

            boolean found = false;
            for(Professor p : professors){
                if(p.getProfessor_idx().equals(extractedIdx(dto.getProfessor()))){
                    found = true;
                    break;
                }
            }
            if(!found){
                notFoundStudents.append(index).append(", ");
            }
            log.info("index"+index);
            index++;

        }

        String msg;
        if(notFoundStudents.length() >0){
            msg = notFoundStudents.substring(0,notFoundStudents.length()-2) + "번의 학생의 학과 혹은 교수정보를 찾을 수 없습니다.";
        }else{

            msg = "성공";
        }
        return msg;

    }



    @Transactional
    public String addStudentList(List<StudentFormDTO> studentList){
        log.info("addStudentList service");
        int index = 0;
        for(StudentFormDTO dto : studentList) {
            System.err.println(index + "번 학생 추가 시작");
            String salt = hashComponent.getRandomSalt();
            String source = dto.getSecurity().substring(dto.getSecurity().length()-7);
            String pw = hashComponent.getHash(source,salt);

            Optional<Major> major = majorRepository.findById(extractedIdx(dto.getMajor()));
            Optional<Professor> professor = professorRepository.findById(extractedIdx(dto.getProfessor()));
            if(major.isPresent() && professor.isPresent()){
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
                System.err.println(index + "번 학생 유저저장");

                System.err.println(major.get().getName() + "major");

                System.err.println(professor.get().getProfessor_idx() + "getProfessor_idx");
                Student s = new Student(
                        dto.getStudent_grade(),
                        user,
                        professor.get(),
                        major.get(),
                        dto.getEntranceDate(),
                        major.get().getIdx()
                );
                System.err.println(index + "번 학생엔티티");
                Situation situation = new Situation(
                        s,
                        Status_type.재학
                );
                SituationRecord situationRecord = new SituationRecord(
                        Status_type.입학,
                        s
                );
                System.err.println("major" + major.get().getName());
//            System.err.println("professor" + professor.getUser().getUser_name());

                studentRepository.save(s);
                situationRepository.save(situation);
                situationRecordRepository.save(situationRecord);
                log.info("s.getUser().getUser_id()"+ s.getUser().getUser_id());



                log.info("index"+index);
                index++;
            }

        }

        return index + "명의 학생 등록에 성공하였습니다.";
    }







    public List<StudentFormDTO> saveStudentDTOList(MultipartFile studentFile) {
        log.info("saveStudentDTOList service");
        List<StudentFormDTO> studentFormDTOList = new ArrayList<>();
        long idx = 1;

//      엑셀 파일을 읽기 위한 리소스를 선언하고 관리.리소스 사용후 자동으로 close() 메서드를 호출,해제
        try (InputStream inputStream = studentFile.getInputStream();//업로드된 파일로부터 입력 스트림을 가져옴
             Workbook workbook = new XSSFWorkbook(inputStream))//XSSFWorkbook 객체를 생성하여 엑셀 파일을 열고 읽음..xlsx 형식의 엑셀 파일을 처리하기 위한 클래스
        {
            Sheet sheet = workbook.getSheetAt(0);//엑셀 파일의 첫 번째 시트 가져오기
            Iterator<Row>  iterator = sheet.iterator();// 시트의 행 반복을 위한 변수

            if(iterator.hasNext()){// 첫 번째 행은 헤더이므로 스킵
                iterator.next();
            }

            int rowIndex = 0;
            while(iterator.hasNext()) {
                Row currentRow = iterator.next();
                rowIndex++;

                if(rowIndex < 2) { // 첫 번째와 두 번째 행은 스킵
                    continue;
                }

                Iterator<Cell> cellIterator = currentRow.iterator();    // 현재 행의 각 셀 반복
                StudentFormDTO student = new StudentFormDTO();

                int cellIndex = 0;
                boolean hasData = false; // 더 이상 읽어들일 데이터가 없는지 확인하기 위한 플래그

                String mInfo = "";
                while(cellIterator.hasNext()){
                    Cell currentCell = cellIterator.next();  // 현재 셀을 가져오기

                    switch (cellIndex){
//                        case 0:
//                            if(currentCell.getCellType() == CellType.NUMERIC){   // 셀의 데이터유형이 숫자인지 문자인지 확인
//                                student.setStudent_num((int) currentCell.getNumericCellValue()); // 숫자형 데이터 가져오기
////                                log.info("getStudent_num : " + student.getStudent_num());
//                                hasData = true; // 데이터가 있으면 플래그를 설정
//                            }
//                            break;
                        case 0:
                            if(currentCell.getCellType() == CellType.STRING){
                                student.setName(currentCell.getStringCellValue());
                                hasData = true; // 데이터가 있으면 플래그를 설정
                                log.info("setName : " + student.getName());
                            }
                            break;
                        case 1:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                // 숫자 형식으로 저장된 셀에서도 문자열 값을 가져옴
                                long securityValue = (long) currentCell.getNumericCellValue();
                                student.setSecurity(String.format("%013d", securityValue));

                                log.info("getSecurity : " + student.getSecurity());
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                student.setSecurity(currentCell.getStringCellValue());

                                log.info("getSecurity : " + student.getSecurity());
                            }
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                // 숫자 형식으로 저장된 셀에서도 문자열 값을 가져옴
                                long pnumValue = (long) currentCell.getNumericCellValue();
                                student.setPnum(String.format("%011d", pnumValue));
                                log.info("getPnum : " + student.getPnum());
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                student.setPnum(currentCell.getStringCellValue());
                                log.info("getPnum : " + student.getPnum());
                            }
                            break;

                        case 3:
                            if(currentCell.getCellType() == CellType.STRING){
                                student.setAddress(currentCell.getStringCellValue());
                                log.info("getAddress : " + student.getAddress());
                            }
                            break;
                        case 4:
                            if(currentCell.getCellType() == CellType.STRING){
                                student.setEmail(currentCell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.STRING) {
                                // 문자열로 저장된 셀에서 날짜 값을 읽어옵니다.
                                String excelDate = currentCell.getStringCellValue();
                                // 엑셀 날짜 값을 java.sql.Date로 변환합니다.
                                Date entranceDate = convertExcelDateToSqlDate(excelDate);
                                student.setEntranceDate(entranceDate);
                            }
                            break;
                        case 6:
                            if(currentCell.getCellType() == CellType.NUMERIC){
                                student.setStudent_grade((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 7:
                            if(currentCell.getCellType() == CellType.STRING){
                                Major major =  majorRepository.findByName(currentCell.getStringCellValue());
//                                log.info("major : " + major);
                                String majorInfo;

                                if(major == null) {
                                    List<Major> majors = majorRepository.findByNameContaining(currentCell.getStringCellValue());
                                    if(majors.isEmpty()){
                                        majorInfo = "학과정보없음";
                                    }else {
                                        mInfo = majors.get(0).getName();
                                        majorInfo = mInfo + "("+majors.get(0).getIdx()+")";
                                    }
                                }else{
                                    mInfo = major.getName();
                                    majorInfo = mInfo + "("+major.getIdx()+")";
                                }
                                log.info(" case 8 majorInfo" + majorInfo);
                                student.setMajor(majorInfo);
                                log.info("getMajor : " + student.getMajor());
                            }
                            break;
                        case 8:
                            if(currentCell.getCellType() == CellType.STRING){
                                String professorName = currentCell.getStringCellValue();
                                log.info("case 9 mInfo :" + mInfo);
                                log.info("case 9 professorName :" + professorName);

                                List<ProfessorUserDto> professorList = professorRepository.findByMajorAndUserUserNameContainingDto(mInfo,professorName);


                                String professorInfo = getString(professorList);
                                student.setProfessor(professorInfo);
                                log.info(" case 9 professorInfo" + professorInfo);
                                log.info("getProfessor : " + student.getProfessor());
                            }
                            break;
                    }
                    cellIndex++;
                }
                // 데이터가 없으면 더 이상 읽어들일 데이터가 없는 것으로 간주하고 반복문 종료
                if(!hasData){
                    break;
                }
                student.setIdx(idx++);
                studentFormDTOList.add(student);
                log.error("studentFormDTO : "+ student);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  studentFormDTOList;
    }



    private static String getString(List<ProfessorUserDto> professorList) {
        String professorInfo;
        if(!professorList.isEmpty()){
            professorInfo = professorList.get(0).getName() + "(" + professorList.get(0).getProfessor_idx() + ")";
//                                    log.info("userNum: " + userNum);
//                                    log.info("userName: " + userName);
        }else{
            professorInfo = "교수정보없음";
        }
        return professorInfo;
    }

    // 엑셀의 날짜 값을 java.sql.Date로 변환하는 메서드
    private Date convertExcelDateToSqlDate(String excelDate) {
        try {
            // 엑셀에서 입력된 날짜 형식에 맞게 SimpleDateFormat을 설정합니다.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            // SimpleDateFormat을 사용하여 문자열을 java.util.Date로 파싱합니다.
            java.util.Date utilDate = dateFormat.parse(excelDate);
            // java.util.Date를 java.sql.Date로 변환하여 반환합니다.
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            // 변환 실패 시에는 예외를 처리하거나 null을 반환합니다.
            return null;
        }
    }

    public List<MajorDto> getMajorList(String collegeName) {
        List<Major> List = majorRepository.findByCollegeName(collegeName);
        List<MajorDto> majorList = new ArrayList<>();
        long idx = 1;

        if(!List.isEmpty()){
            for (Major m : List){
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
        return professorRepository.searchByMajorAndProfessorAndLeave(map, pageable);
    }


    public ProfessorListDto selectOneProfessor(Long idx) {
        return professorRepository.selectOneProfessor(idx);
    }

    @Transactional
    public Professor updateProfessorByManager(HashMap<String, Object> map) {
        Long idx = (Long)map.get("idx");
        java.util.Date hireDate = (java.util.Date) map.get("hireDate");
        java.util.Date leaveDate = (java.util.Date) map.get("leaveDate");
        Professor professor = professorRepository.findById(idx).get();
        professor.setHireDate(new Date(hireDate.getTime()));
        professor.setLeaveDate(new Date(leaveDate.getTime()));

        return professor;
    }

    @Transactional
    public Professor professorDel(HashMap<String, Object> map) {
        Long idx = (Long)map.get("idx");
        java.util.Date leaveDate = (java.util.Date) map.get("leaveDate");
        Professor professor = professorRepository.findById(idx).get();
        professor.setLeave(YesOrNo.Y);
        professor.setLeaveDate(new Date(leaveDate.getTime()));
        return professor;
    }

    public Page<StudentListDto> selectAllStudent(HashMap<String, Object> map, Pageable pageable) {
        return studentRepository.selectAllStudent(map, pageable);
    }

    public StudentListDto selectOneStudent(Long idx) {
        return studentRepository.selectOndeStudent(idx);
    }

    @Transactional
    public Student studentUpdateByManager(Long idx, java.util.Date entranceDate) {
        Student student = studentRepository.findById(idx).get();
        Date date = new Date(entranceDate.getTime());
        student.setEnteranceDate(date);
        return student;
    }

    public ManagerDTO selectOneManager(Long idx) {
        return managerRepository.selectOneManager(idx);
    }

    @Transactional
    public Manager updateManagerByManager(HashMap<String, Object> map) {
        Long idx = (Long)map.get("idx");
        Manager manager = managerRepository.findById(idx).get();
        java.util.Date hireDate = (java.util.Date)map.get("hireDate");
        Date date = new Date(hireDate.getTime());
        manager.setHireDate(date);

        if(map.get("leaveDate") != null) {
            java.util.Date leaveDate = (java.util.Date)map.get("leaveDate");
            Date date2 = new Date(leaveDate.getTime());
            manager.setLeaveDate(date2);
        }
        return manager;
    }

    @Transactional
    public Manager managerDel(Map<String, Object> map) {
        Long idx = (Long)map.get("idx");
        java.util.Date leaveDate = (java.util.Date)map.get("leaveDate");
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
        log.info("name : " + str.substring(0,end));
        return str.substring(0,end);
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
}


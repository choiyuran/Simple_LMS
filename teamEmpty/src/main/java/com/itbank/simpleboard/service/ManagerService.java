package com.itbank.simpleboard.service;

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
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        // 새로운 파일 이름 생성 (사용자 이름과 주민등록번호로 조합)
        String newFileName = userName + "_" + dto.getFrontSecurity();
        String professor_img = fileComponent.uploadIdPhoto(dto.getImageFile(), "idPhoto_professor",newFileName);
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

            while(iterator.hasNext()){  // 두번째 행부터 반복
                Row currentRow = iterator.next();   // 현재 행 가져오기
                Iterator<Cell> cellIterator = currentRow.iterator();    // 현재 행의 각 셀 반복
                StudentFormDTO student = new StudentFormDTO();

                int cellIndex = 0;
                boolean hasData = false; // 더 이상 읽어들일 데이터가 없는지 확인하기 위한 플래그

                while(cellIterator.hasNext()){
                    Cell currentCell = cellIterator.next();  // 현재 셀을 가져오기

                    switch (cellIndex){
                        case 0:
                            if(currentCell.getCellType() == CellType.NUMERIC){   // 셀의 데이터유형이 숫자인지 문자인지 확인
                                student.setStudent_num((int) currentCell.getNumericCellValue()); // 숫자형 데이터 가져오기
                                hasData = true; // 데이터가 있으면 플래그를 설정
                            }
                            break;
                        case 1:
                            if(currentCell.getCellType() == CellType.STRING){
                                student.setName(currentCell.getStringCellValue());
                            }
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                // 숫자 형식으로 저장된 셀에서도 문자열 값을 가져옴
                                long securityValue = (long) currentCell.getNumericCellValue();
                                student.setSecurity(String.format("%013d", securityValue));
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                student.setSecurity(currentCell.getStringCellValue());
                            }
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                // 숫자 형식으로 저장된 셀에서도 문자열 값을 가져옴
                                long pnumValue = (long) currentCell.getNumericCellValue();
                                student.setPnum(String.format("%011d", pnumValue));
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                student.setPnum(currentCell.getStringCellValue());
                            }
                            break;

                        case 4:
                            if(currentCell.getCellType() == CellType.STRING){
                                student.setAddress(currentCell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if(currentCell.getCellType() == CellType.STRING){
                                student.setEmail(currentCell.getStringCellValue());
                            }
                            break;
                        case 6:
                            if (currentCell.getCellType() == CellType.STRING) {
                                // 문자열로 저장된 셀에서 날짜 값을 읽어옵니다.
                                String excelDate = currentCell.getStringCellValue();
                                // 엑셀 날짜 값을 java.sql.Date로 변환합니다.
                                Date entranceDate = convertExcelDateToSqlDate(excelDate);
                                student.setEntranceDate(entranceDate);
                            }
                            break;
                        case 7:
                            if(currentCell.getCellType() == CellType.NUMERIC){
                                student.setStudent_grade((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 8:
                            if(currentCell.getCellType() == CellType.NUMERIC){
                                student.setMajor((long) currentCell.getNumericCellValue());
                            }
                            break;
                        case 9:
                            if(currentCell.getCellType() == CellType.NUMERIC){
                                student.setProfessor((long) currentCell.getNumericCellValue());
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
                System.err.println("studentFormDTO : "+ student.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  studentFormDTOList;
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
    public List<ProfessorListDto> searchByMajorAndProfessorAndLeave(HashMap<String, Object> map) {
        return professorRepository.searchByMajorAndProfessorAndLeave(map);
    }

    public List<ProfessorListDto> searchByMajorAndProfessor(HashMap<String, Object> map) {
        return professorRepository.searchByMajorAndProfessor(map);
    }

    public ProfessorListDto selectOneProfessor(Long idx) {
        return professorRepository.selectOneProfessor(idx);
    }

    @Transactional
    public Professor updateProfessorByManager(Long idx, java.util.Date hireDate) {
        Professor professor = professorRepository.findById(idx).get();
        java.util.Date date = hireDate;
        Date sqlDate = new Date(date.getTime());
        professor.setHireDate(sqlDate);
        return professor;
    }

    @Transactional
    public Professor professorDel(Long idx) {
        Professor professor = professorRepository.findById(idx).get();
        professor.setLeave(YesOrNo.Y);
        professor.setLeaveDate(Date.valueOf(LocalDate.now()));
        return professor;
    }



}


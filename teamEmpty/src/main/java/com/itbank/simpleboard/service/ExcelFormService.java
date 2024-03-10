package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.dto.StudentFormDTO;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ExcelFormService {


    private final MajorRepository majorRepository;
    private final ProfessorRepository professorRepository;

    public List<StudentFormDTO> saveStudentDTOList(MultipartFile studentFile) {
        log.info("saveStudentDTOList service");
        List<StudentFormDTO> studentFormDTOList = new ArrayList<>();
        long idx = 1;

//      엑셀 파일을 읽기 위한 리소스를 선언하고 관리.리소스 사용후 자동으로 close() 메서드를 호출,해제
        try (InputStream inputStream = studentFile.getInputStream();//업로드된 파일로부터 입력 스트림을 가져옴
             Workbook workbook = new XSSFWorkbook(inputStream))//XSSFWorkbook 객체를 생성하여 엑셀 파일을 열고 읽음..xlsx 형식의 엑셀 파일을 처리하기 위한 클래스
        {
            Sheet sheet = workbook.getSheetAt(0);//엑셀 파일의 첫 번째 시트 가져오기
            Iterator<Row> iterator = sheet.iterator();// 시트의 행 반복을 위한 변수

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
                                    if(majors.isEmpty() || currentCell.getStringCellValue().length() < 3){
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
}

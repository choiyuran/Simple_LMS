package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.entity.Manager;
import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.repository.manager.CollegeRepository;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import com.itbank.simpleboard.repository.manager.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;

    public List<ManagerDTO> findAllManager() {
        List<Manager> managerList = managerRepository.findAll();
        List<ManagerDTO> managerDTOList = new ArrayList<>();

        for(Manager m : managerList){
            ManagerDTO dto = new ManagerDTO();
            dto.setManagerImg(m.getManager_img());
            dto.setMamagerId(m.getUser().getUser_id());
            dto.setMamagerName(m.getUser().getUser_name());
            dto.setMamagerPnum(m.getUser().getPnum());
            dto.setManagerEmail(m.getUser().getEmail());
            dto.setManagerHireDate(m.getHireDate());
            managerDTOList.add(dto);
        }

        return managerDTOList;
    }

    public List<College> selectAllCollege() {
        return collegeRepository.findAll();
    }


    public Major addMajor(MajorDto major) {
        College college = collegeRepository.findById(major.getCollege_idx()).get();
        Major major1 = new Major(major.getName(),major.getTuition(),college);
        return majorRepository.save(major1);

    }
}

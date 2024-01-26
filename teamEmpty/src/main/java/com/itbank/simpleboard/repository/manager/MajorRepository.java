package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByAbolition(YesOrNo yesOrno);

    List<Major> findByNameContaining(String majorName);

    List<Major> searchByCollegeAndNameContaining(College college, String majorName);

    List<Major> findByCollege(College college);
}

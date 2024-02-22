package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.YesOrNo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByAbolition(YesOrNo yesOrno);

    Page<Major> findByAbolition(Pageable pageable, YesOrNo yesOrno);

    List<Major> findByNameContaining(String majorName);

    Page<Major> searchByCollegeAndNameContaining(College college, String majorName, Pageable pageable);

    Page<Major> findByCollege(College college, Pageable pageable);
    List<Major> findByCollegeName(String collegeName);
    Major findByName(String majorName);

    Page<Major> findByNameContaining(String majorName, Pageable pageable);
}

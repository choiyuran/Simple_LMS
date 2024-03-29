package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop5ByOrderByIdxDesc();

    Page<Notice> findAllByOrderByIdxDesc(Pageable pageable);
}

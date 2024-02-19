package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

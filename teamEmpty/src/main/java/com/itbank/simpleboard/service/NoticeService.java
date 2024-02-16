package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Notice;
import com.itbank.simpleboard.repository.manager.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public List<Notice> selectAll() {
        return noticeRepository.findAll();
    }
}

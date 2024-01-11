package com.itbank.simpleboard.service;

import com.itbank.simpleboard.repository.manager.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
}

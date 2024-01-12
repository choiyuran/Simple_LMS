package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}

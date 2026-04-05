package com.banka1.order.repository;

import com.banka1.order.entity.ActuaryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActuaryInfoRepository extends JpaRepository<ActuaryInfo, Long> {
    Optional<ActuaryInfo> findByEmployeeId(Long employeeId);
}

package com.harshi_solution.inventorymate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.harshi_solution.inventorymate.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	@Query("SELECT SUM(p.payAmount) FROM Payment p WHERE p.payType = :payType")
	Long sumPayAmountByPayType(@Param("payType") String payType);

}

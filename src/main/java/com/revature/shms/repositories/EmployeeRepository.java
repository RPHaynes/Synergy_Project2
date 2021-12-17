package com.revature.shms.repositories;

import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Page<Employee> findAllByOrderByEmployeeType(Pageable pageable);
	Page<Employee> findByEmployeeType(EmployeeType employeeType,Pageable pageable);
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findByUsername(String username);
	Optional<Employee> findByEmployeeID(int employeeID);
}

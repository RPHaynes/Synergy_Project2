package com.revature.shms.repositories;

import com.revature.shms.models.Cleaning;
import com.revature.shms.models.Employee;
import com.revature.shms.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CleaningRepository  extends JpaRepository<Cleaning,Integer> {
	Page<Cleaning> findAllByOrderByPriorityDescDateAddedAsc(Pageable pageable);
	Page<Cleaning> findAllByEmployeeOrderByPriorityDescDateAddedAsc(Employee employee, Pageable pageable);

	Optional<Cleaning> findByRoom(Room room);
}

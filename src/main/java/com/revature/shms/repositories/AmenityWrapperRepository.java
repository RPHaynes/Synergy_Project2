package com.revature.shms.repositories;
import com.revature.shms.enums.Amenities;
import com.revature.shms.models.AmenityWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AmenityWrapperRepository extends JpaRepository<AmenityWrapper, Integer> { }

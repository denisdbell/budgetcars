package com.budgetcars.net.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bugdetcars.net.model.Vehicle;


public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
	@Query("SELECT v FROM Vehicle v WHERE UPPER(v.make) like :make OR UPPER(v.model) like :model OR UPPER(v.year) like :year")
	Page<Vehicle> findAllVehicles (@Param("make") String make,@Param("model") String model,@Param("year") String year, Pageable pageable);
}

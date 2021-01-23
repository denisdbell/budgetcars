package com.budgetcars.net.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugdetcars.net.model.Vehicle;


public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
}

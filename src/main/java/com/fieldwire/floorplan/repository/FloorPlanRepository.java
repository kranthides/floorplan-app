package com.fieldwire.floorplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fieldwire.floorplan.model.FloorPlan;
import com.fieldwire.floorplan.model.Project;

@Repository
public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {

}

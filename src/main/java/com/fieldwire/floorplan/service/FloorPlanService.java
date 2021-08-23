package com.fieldwire.floorplan.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fieldwire.floorplan.model.FloorPlan;

@Service
public interface FloorPlanService {

	public Long createFloorPlan(FloorPlan floorPlan,long projectId ,MultipartFile file);
	public String deleteFloorPlan(long id);
	public FloorPlan getFloorPlan(long id);
	public Long updateFloorPlan(FloorPlan floorPlan,long projectId, MultipartFile multiPartFile);		

}

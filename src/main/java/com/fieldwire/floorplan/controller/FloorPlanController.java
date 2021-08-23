package com.fieldwire.floorplan.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fieldwire.floorplan.exception.InvalidRequestException;
import com.fieldwire.floorplan.model.FloorPlan;
import com.fieldwire.floorplan.model.FloorPlanDto;
import com.fieldwire.floorplan.service.FloorPlanService;
import com.fieldwire.floorplan.service.ProjectService;
import com.fieldwire.floorplan.util.GlobalConstants;

@RestController
@RequestMapping("/api/floorplan")
public class FloorPlanController {
	@Autowired
	FloorPlanService floorPlanService; 
	
	
	@PostMapping 
	public Long saveProject(@ModelAttribute FloorPlanDto req ,@RequestParam(value = "file", required=true) MultipartFile file) { 
		
		FloorPlan floorPlan = new FloorPlan(); 
		floorPlan.setId(req.getId());
		floorPlan.setName(req.getName());
		if(req.getProjectId() == null) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST.toString(), "Project ID is Required");
		}

		return floorPlanService.createFloorPlan(floorPlan,req.getProjectId(), file);
	}
	
	@GetMapping(path = "/{id}")
	public FloorPlan getProject(@Valid @PathVariable long id) { 
		return floorPlanService.getFloorPlan(id);	
	}
	
	@PatchMapping
	public Long updateProject(@ModelAttribute FloorPlanDto req ,@RequestParam(value = "file", required=true) MultipartFile file) { 
		FloorPlan floorPlan = new FloorPlan(); 
		floorPlan.setId(req.getId());
		floorPlan.setName(req.getName());
		
		if(req.getProjectId() == null ||req.getId() ==null) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST.toString(), "Project ID/FloorPlan Id is Required");
		}


		return floorPlanService.updateFloorPlan(floorPlan,req.getProjectId(), file);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable long id) { 
		floorPlanService.deleteFloorPlan(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
	}

}

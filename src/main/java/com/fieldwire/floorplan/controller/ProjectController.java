package com.fieldwire.floorplan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.fieldwire.floorplan.model.Project;
import com.fieldwire.floorplan.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	ProjectService projectService; 
	
	@PostMapping 
	public ResponseEntity<Long> saveProject(@RequestBody Project project) { 
		return new ResponseEntity<>(projectService.createProject(project),HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/{id}")
	public  ResponseEntity<Project>  getProject(@Valid @PathVariable Long id) { 
		return new ResponseEntity<>(projectService.getProject(id),HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<Long>  updateProject(@RequestBody Project project) { 
		return new ResponseEntity<>(projectService.updateProject(project),HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable Long id) { 
		projectService.deleteProject(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
	}

}

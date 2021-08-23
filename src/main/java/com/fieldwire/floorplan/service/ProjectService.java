package com.fieldwire.floorplan.service;

import org.springframework.stereotype.Service;

import com.fieldwire.floorplan.model.Project;

@Service
public interface ProjectService {
	public Long createProject(Project project);
	public void deleteProject(Long id);
	public Project getProject(Long id);
	public Long updateProject(Project project);		
}

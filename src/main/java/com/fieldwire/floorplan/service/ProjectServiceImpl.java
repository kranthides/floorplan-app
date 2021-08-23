package com.fieldwire.floorplan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fieldwire.floorplan.exception.InvalidRequestException;
import com.fieldwire.floorplan.exception.RecordsNotFoundException;
import com.fieldwire.floorplan.model.FloorPlan;
import com.fieldwire.floorplan.model.Project;
import com.fieldwire.floorplan.repository.ProjectRepository;
import com.fieldwire.floorplan.util.AWSS3Service;
import com.fieldwire.floorplan.util.GlobalConstants;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	FloorPlanService floorPlanService;
	
	@Autowired
	AWSS3Service s3Service; 


    @Value("${aws.s3.floorplan.url}")
    private String floorPlanUrl;

	public Long createProject(Project project) {
		Project p=  projectRepository.save(project);		
		return p.getId();
	}

	public void deleteProject(Long id) {
		Project project = getProject(id);
		
		for(FloorPlan floorplan : project.getFloorPlans()) {
			s3Service.deleteFile(floorplan);
		}

		projectRepository.deleteById(id);
	}

	public Project getProject(Long id) {
		Optional<Project> p = projectRepository.findById(id);
		
		if(!p.isPresent()) {
			throw new RecordsNotFoundException("Project Id ->"+id , "Project Id: "+id + " -> " +GlobalConstants.NO_RECORDS_FOUND); 
		}
		p.get().setImageUrl(floorPlanUrl);
		return p.get();	
	}

	public Long updateProject(Project project) {
		getProject(project.getId());
		
		Project p=  projectRepository.save(project);		
		return p.getId();
	}

}

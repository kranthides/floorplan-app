package com.fieldwire.floorplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fieldwire.floorplan.exception.RecordsNotFoundException;
import com.fieldwire.floorplan.model.FloorPlan;
import com.fieldwire.floorplan.model.Project;
import com.fieldwire.floorplan.repository.FloorPlanRepository;
import com.fieldwire.floorplan.util.AWSS3Service;
import com.fieldwire.floorplan.util.GlobalConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class FloorPlanServiceImpl implements FloorPlanService {
    private static final Logger log = LoggerFactory.getLogger(FloorPlanServiceImpl.class);

	@Autowired
	AWSS3Service s3Service; 
	@Autowired
	FloorPlanRepository floorPlanRepository;
	@Autowired
	ProjectService projectService;

	
 

	@Override
	public Long createFloorPlan(FloorPlan floorPlan,long projectId, MultipartFile multiPartFile) {
		
		Project project = projectService.getProject(projectId);
		
        File file = convertMultiPartFileToFile(multiPartFile);
        generateFileName(floorPlan, projectId, file.getName());
        
		s3Service.uploadFile(file,floorPlan);
			
		floorPlan.setProject(project);
		FloorPlan f=  floorPlanRepository.save(floorPlan);		
		return f.getId();

 	}

	@Override
	public String deleteFloorPlan(long id) {
		FloorPlan floorplan = getFloorPlan(id);
		s3Service.deleteFile(floorplan);
		
		floorPlanRepository.deleteById(id);
		return "success";
	}

	@Override
	public FloorPlan getFloorPlan(long id) {
		
		Optional<FloorPlan> f = floorPlanRepository.findById(id);
		
		if(!f.isPresent()) {
			throw new RecordsNotFoundException("Floorplan Id ->"+id , "FloorPlan Id: "+id + " -> " +GlobalConstants.NO_RECORDS_FOUND); 
		}
  
 		return f.get();
	}

	@Override
	public Long updateFloorPlan(FloorPlan floorPlan,long projectId, MultipartFile multiPartFile) {
		
		// Getting Floorplan Information from DB 
		FloorPlan floorplan = getFloorPlan(floorPlan.getId());
		s3Service.deleteFile(floorplan);

		// Converting the floorplan multipart file to File 
        File file = convertMultiPartFileToFile(multiPartFile);
        generateFileName(floorPlan, projectId, file.getName());
        
        //Uploading the files 
		s3Service.uploadFile(file,floorPlan);

		FloorPlan f=  floorPlanRepository.save(floorPlan);		
		return f.getId();

	}
	
	
	
	private void generateFileName(FloorPlan floorPlan, long projectId, String fileName) {
		
		String fileNameWithOutExtension = FilenameUtils.getBaseName(fileName);
		String fileNameExtenstion = FilenameUtils.getExtension(fileName);
		String originalFileName = projectId+"_"+fileName; 
		String thumbnailFileName =  projectId+"_"+fileNameWithOutExtension+"_thumb."+fileNameExtenstion; 
		String largeFileName =  projectId+"_"+fileNameWithOutExtension+"_large."+fileNameExtenstion;; 
		
		floorPlan.setOriginal(originalFileName);
		floorPlan.setLarge(largeFileName);
		floorPlan.setThumbNail(thumbnailFileName);
		
	}
	
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            log.error("Error converting the multi-part file to file= ", ex.getMessage());
        }
        return file;
    }
    


 
}

package com.fieldwire.floorplan.util;

import java.io.File;

import com.fieldwire.floorplan.model.FloorPlan;

public interface AWSS3Service {

	void uploadFile(File file,FloorPlan floorPlan);
	
	void deleteFile(FloorPlan floorPlan);

}

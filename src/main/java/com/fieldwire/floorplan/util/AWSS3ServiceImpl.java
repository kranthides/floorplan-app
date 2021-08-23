package com.fieldwire.floorplan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
 
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.clouddirectory.model.DeleteObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fieldwire.floorplan.model.FloorPlan;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private static final Logger log = LoggerFactory.getLogger(AWSS3ServiceImpl.class);
    
    @Autowired
    private AmazonS3 amazonS3;
    
    @Value("${aws.s3.bucket}")
    private String bucketName;
 
    
    @Override
    // @Async annotation ensures that the method is executed in a different background thread 
    // but not consume the main thread.
    @Async
    public void uploadFile(final File file, FloorPlan floorPlan) {
        log.info("File upload in progress.");
        try {
        	uploadOriginal(floorPlan, file);
            uploadThumbNail(floorPlan, file);
            uploadLargeFile(floorPlan, file);

            log.info("File upload is completed.");
            file.delete();  // To remove the file locally created in the project folder.
        } catch (final AmazonServiceException ex) {
            log.info("File upload is failed.");
            log.error("Error= {} while uploading file.", ex.getMessage());
        }
        catch (final IOException ex) {
            log.info("File upload is failed.");
            log.error("Error= {} while uploading file.", ex.getMessage());
        }
    }
 
    private File uploadThumbNail(FloorPlan floorPlan, final File file) throws IOException {
		String fileExtenstion = FilenameUtils.getExtension(floorPlan.getThumbNail());

 
    	File thumbNailOutput = new File(floorPlan.getThumbNail());
    	Thumbnails.of(file).size(100, 100).outputFormat(fileExtenstion).toFile(thumbNailOutput);

    	uploadFileToS3Bucket(bucketName, thumbNailOutput, floorPlan.getThumbNail());
    	
    	thumbNailOutput.delete();
        return file;
    }
    
    private File uploadLargeFile(FloorPlan floorPlan, final File file) throws IOException {
    	
		String fileExtenstion = FilenameUtils.getExtension(floorPlan.getLarge());

    	File largeFile = new File(floorPlan.getLarge());
    	Thumbnails.of(file).size(2000, 2000).outputFormat(fileExtenstion).toFile(largeFile);

    	uploadFileToS3Bucket(bucketName, largeFile,floorPlan.getLarge());
    	
    	  if (!largeFile.delete()) {
    		    // file delete failed; take appropriate action
    	  }

    	largeFile.delete();
        return file;
    }

    private File uploadOriginal(FloorPlan floorPlan, final File file) throws IOException {
    	
    	uploadFileToS3Bucket(bucketName, file,floorPlan.getOriginal());
    	
         return file;
    }

 
    private void uploadFileToS3Bucket(final String bucketName, final File file, String fileName) {
         log.info("Uploading file with name= " + fileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        amazonS3.putObject(putObjectRequest);
    }

	@Override
	public void deleteFile(FloorPlan floorPlan) {
		String[] objectKeys = {floorPlan.getOriginal(), floorPlan.getThumbNail(), floorPlan.getLarge()}; 
        DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName)
                .withKeys(objectKeys);
        log.info("Deleting the files= " + Arrays.toString(objectKeys));

		amazonS3.deleteObjects(dor);
	}
}
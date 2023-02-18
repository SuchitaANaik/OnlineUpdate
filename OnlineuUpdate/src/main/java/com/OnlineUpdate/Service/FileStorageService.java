package com.OnlineUpdate.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.OnlineUpdate.Dao.UploadFileResponseRepository;
import com.OnlineUpdate.Entity.FileStoragePojo;
import com.OnlineUpdate.Entity.UploadFileResponse;
import com.OnlineUpdate.Exception.FileStorageException;
import com.OnlineUpdate.Exception.MentionedFileNotFoundException;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    
    @Autowired
    FileStoragePojo fileStorageProperties;
    
    @Autowired
    UploadFileResponseRepository uploadFileResponse;

   
    public FileStorageService(FileStoragePojo fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public boolean saveFile(UploadFileResponse response) {
    	
    	boolean result=false;
    	try {
    		
    		UploadFileResponse up= uploadFileResponse.save(response);
    		
    		if(up!=null)
    			result=true;
    	}
    	catch(Exception e) {
    		
    		e.printStackTrace();
    	}
		return result;
    }

 public List<UploadFileResponse> getFiles() {
    	
    	List<UploadFileResponse> up= new ArrayList<UploadFileResponse>();
    	try {
    		
    		up= uploadFileResponse.getResponseData();
    	}
    	catch(Exception e) {
    		
    		e.printStackTrace();
    	}
		return up;
    }
 
 public boolean updateStatus(Long id) {
 	
 	try {
 		
			
 			int i= uploadFileResponse.updateStatus(id);
 			
 			System.out.println("Update count="+i);
 		
 	}
 	catch(Exception e) {
 		
 		e.printStackTrace();
 	}
		return true;
 }

    
    public List<UploadFileResponse> getFileVersion(String version) {
    	
    	List<UploadFileResponse> up= new ArrayList<UploadFileResponse>();
    	try {
    		
    		up= uploadFileResponse.findByVersion(version);
    	}
    	catch(Exception e) {
    		
    		e.printStackTrace();
    	}
		return up;
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
           
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MentionedFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MentionedFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
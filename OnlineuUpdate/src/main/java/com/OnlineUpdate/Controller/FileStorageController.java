package com.OnlineUpdate.Controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.OnlineUpdate.Entity.UploadFileResponse;
import com.OnlineUpdate.Service.FileStorageService;




@RestController
public class FileStorageController {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
	  @GetMapping("/uploadfile") 
	  public ModelAndView uploadFile() { 
		  ModelAndView mav = new ModelAndView("uploadfile"); // mav.addObject("user", new Login());
	  mav.addObject("errorMsg", ""); 
	  return mav; 
	  }

	@PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		
		String errMsg="Wrong File Name ( Please upload file nomenclature as: *_V1.*.db OR *_V1.*.jar)";
		Date dt= new Date();
		UploadFileResponse uploadfile=new UploadFileResponse();
		
    	
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
              
        if(fileName!=null && fileName.contains("_V")){
        	
        	String[] strAr1=new String[] {"_V1.1.db", "_V1.1.jar","_V1.2.db","_V1.2.jar","_V1.3.db","_V1.3.jar","_V1.4.db","_V1.4.jar","_V1.5.db","_V1.5.jar"};
        	
            Boolean result=false;
            
            for(String str: strAr1){
                String nomenCl=str;
                if(nomenCl!=null){
                     String pattern = "^"+"*\\"+nomenCl+"$";
                     Pattern r = Pattern.compile(pattern);
                     Matcher m = r.matcher(fileName);
                     if(m.find()){
                    	 result=true;
    						break;
    					}
                }
            }
            
            System.out.println("Filename="+fileName+"::"+result);
            
            if(result) {
            	
            	boolean dbfileP=false;
            	boolean jarfileP=false;
	        String versioName=fileName.substring(fileName.lastIndexOf("_V")+1,fileName.lastIndexOf("."));
	        String extesion=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
	       List<UploadFileResponse> up1 =fileStorageService.getFileVersion(versioName);
	        
	       for (UploadFileResponse uploadFileResponse1 : up1) {
	    	   
	    	   if(uploadFileResponse1.getExtension().equals("db"))
	    		   dbfileP=true;   
	    	   if(uploadFileResponse1.getExtension().equals("jar"))
	    		   jarfileP=true; 
		}
	       
	       if((extesion.equals("db") && !dbfileP) || (extesion.equals("jar")&& !jarfileP))	     {  
	        uploadfile= new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize(),extesion,versioName,"N",dt);
	        
	       fileStorageService.saveFile(uploadfile);
	       }
	       else {
	    	   errMsg="File with same version is already present in DB";
	    	   uploadfile.setFileName(errMsg);
	       }
	       
            }
	        else {
	        	uploadfile.setFileName(errMsg);
	        }
        }
        else
        	uploadfile.setFileName(errMsg);
        
        return uploadfile;
    }
    
	
	 @GetMapping("/downloadFieforUpdater") public ResponseEntity<Map<String, String>> downloadFile() {
		
		 Map<String,String> map= new HashMap<String,String>();
		  System.out.println("********INSIDE DOWNLOAD FUNCTION*********"); 
		  List<UploadFileResponse> up =fileStorageService.getFiles();
		  
		  for (UploadFileResponse uploadFileResponse : up) {
				 map.put(uploadFileResponse.getVersion()+"-"+uploadFileResponse.getExtension(), uploadFileResponse.getFileDownloadUri());
			
				 fileStorageService.updateStatus(uploadFileResponse.getId());
	        
		  }
		  
	  return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	  
	 }
	 
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
    	
    	System.out.println("download filename="+fileName);
        Resource resource = fileStorageService.loadFileAsResource((String)fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
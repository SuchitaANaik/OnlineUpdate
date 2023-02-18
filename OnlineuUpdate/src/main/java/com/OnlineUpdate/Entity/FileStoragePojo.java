package com.OnlineUpdate.Entity;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
	public class FileStoragePojo {

		private String uploadDir;

	    public FileStoragePojo() {
			
		}

		public String getUploadDir() {
	        return uploadDir;
	    }

	    public void setUploadDir(String uploadDir) {
	        this.uploadDir = uploadDir;
	    }
	}
package com.OnlineUpdate.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="UploadFileResponse")
public class UploadFileResponse {

	private Long id;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private String extension;
    private String version;
    private String download_status;
    private Date upload_date;
    
    
    public UploadFileResponse() {
		
	}

	public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size, String extension,
			String version, String download_status, Date upload_date) {
    	
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
		this.extension = extension;
		this.version = version;
		this.download_status = download_status;
		this.upload_date = upload_date;
	}

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "fileName")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "fileDownloadUri")
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	@Column(name = "fileType")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "size")
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	@Column(name = "extension")
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	@Column(name = "download_status")
	public String getDownload_status() {
		return download_status;
	}

	public void setDownload_status(String download_status) {
		this.download_status = download_status;
	}
	@Column(name = "upload_date")
	public Date getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
    
    
    
}
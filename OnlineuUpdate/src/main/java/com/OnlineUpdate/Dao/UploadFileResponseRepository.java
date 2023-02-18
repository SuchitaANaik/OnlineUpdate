package com.OnlineUpdate.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.OnlineUpdate.Entity.Login;
import com.OnlineUpdate.Entity.UploadFileResponse;


@Repository
@Transactional 
public interface UploadFileResponseRepository extends JpaRepository<UploadFileResponse, Long>{
		
	UploadFileResponse save(UploadFileResponse response);
		
	List<UploadFileResponse> findByVersion(String version);
	
	@Query("SELECT UP FROM UploadFileResponse UP WHERE download_status = 'N' ")
    List<UploadFileResponse> getResponseData();
	
	@Modifying
    @Query("UPDATE UploadFileResponse UP SET UP.download_status ='Y' WHERE UP.id = :id")
    public int updateStatus(@Param("id") Long id);
	
}

package com.episen.ing3.tpmra.repository;


import java.awt.List;
import java.awt.print.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.episen.ing3.tpmra.model.Document;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, String>{
	 List findAll(Pageable pageable);
}
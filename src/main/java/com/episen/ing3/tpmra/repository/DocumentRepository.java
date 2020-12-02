package com.episen.ing3.tpmra.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.episen.ing3.tpmra.model.Document;

@Repository
public interface DocumentRepository extends CrudRepository<Document, String>{

}
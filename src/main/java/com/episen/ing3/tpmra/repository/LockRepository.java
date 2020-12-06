package com.episen.ing3.tpmra.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.episen.ing3.tpmra.model.Lock;

@Repository
public interface LockRepository extends CrudRepository<Lock, Integer>{

}
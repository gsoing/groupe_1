package com.episen.ing3.tpmra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.episen.ing3.tpmra.model.Lock;

@Repository
public interface LockRepository extends JpaRepository<Lock, Integer>{

}
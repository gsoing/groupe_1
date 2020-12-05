package com.episen.ing3.tpmra.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

import com.episen.ing3.tpmra.repository.DocumentRepository;
import com.episen.ing3.tpmra.repository.LockRepository;

import com.episen.ing3.tpmra.model.Lock;

@Service
public class LockService {

	@Autowired
	LockRepository lockRepository;
	@Autowired
	DocumentRepository documentRepository;

	public Optional<Lock> getDocumentLock(String documentId) {
		return lockRepository.findById(documentId);

	}

	public Lock putDocumentLock(String documentId) {
		if(documentRepository.findById(documentId).isPresent()) {
			OffsetDateTime dateTime = OffsetDateTime.now();
			Lock lock = new Lock(documentId, null, dateTime);
			return lockRepository.save(lock);
		}
		return null;

	}

	public Boolean deleteDocumentLock(String documentId) {
		lockRepository.deleteById(documentId);
		if(this.getDocumentLock(documentId).isPresent()) {
			return false; 
		}
		return true;
	}
}

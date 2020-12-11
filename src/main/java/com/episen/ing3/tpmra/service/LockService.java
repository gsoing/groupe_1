package com.episen.ing3.tpmra.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.exception.ForbiddenException;
import com.episen.ing3.tpmra.exception.NotFoundException;
import com.episen.ing3.tpmra.model.Lock;
import com.episen.ing3.tpmra.repository.DocumentRepository;
import com.episen.ing3.tpmra.repository.LockRepository;

@Service
public class LockService {

	@Autowired
	LockRepository lockRepository;
	@Autowired
	DocumentRepository documentRepository;

	public Optional<Lock> getDocumentLock(Integer documentId) {
		return lockRepository.findById(documentId);
	}

	public Lock putDocumentLock(Integer documentId, String owner) {
		lockRepository.findById(documentId).ifPresent(lock -> {throw NotFoundException.DEFAULT; });
		if(documentRepository.findById(documentId).isPresent()) {
			OffsetDateTime dateTime = OffsetDateTime.now();
			Lock lock = new Lock(documentId, owner, dateTime);
			return lockRepository.save(lock);
		}
		else throw NotFoundException.DEFAULT;
	}

	public void deleteDocumentLock(Integer documentId, String userName) {
		Lock lock = lockRepository.findById(documentId).orElseThrow(() ->  NotFoundException.DEFAULT);
		if(lock.getOwner().equals(userName)) 
			lockRepository.deleteById(documentId);
		else
			throw ForbiddenException.DEFAULT;
	}
}

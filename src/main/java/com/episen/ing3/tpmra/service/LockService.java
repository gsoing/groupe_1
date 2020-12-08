package com.episen.ing3.tpmra.service;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.model.Lock;
import com.episen.ing3.tpmra.repository.DocumentRepository;
import com.episen.ing3.tpmra.repository.LockRepository;

@Service
public class LockService {

	@Autowired
	LockRepository lockRepository;
	@Autowired
	DocumentRepository documentRepository;

	public Lock getDocumentLock(Integer documentId) {
		Lock lock = lockRepository.findById(documentId).orElseThrow(() -> new NoSuchElementException());
		return lock;
	}

	public Lock putDocumentLock(Integer documentId, String owner) {
		lockRepository.findById(documentId).ifPresent(lock -> {throw new AccessDeniedException("A lock is already put : " + lock); });
		if(documentRepository.findById(documentId).isPresent()) {
			OffsetDateTime dateTime = OffsetDateTime.now();
			Lock lock = new Lock(documentId, owner, dateTime);
			return lockRepository.save(lock);
		}
		else throw new NoSuchElementException();
	}

	public void deleteDocumentLock(Integer documentId, String userName) {
		Lock lock = lockRepository.findById(documentId).orElseThrow(() -> new NoSuchElementException());
		if(lock.getOwner().equals(userName)) 
			lockRepository.deleteById(documentId);
		else
			new AccessDeniedException("The lock was put by another user");
	}
}

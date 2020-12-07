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
		Optional<Lock> optionalLock = lockRepository.findById(documentId);
		if(!optionalLock.isPresent()) throw new NoSuchElementException();
		return optionalLock.get();

	}

	public Lock putDocumentLock(Integer documentId, String owner) {
		if(lockRepository.findById(documentId).isPresent()) throw new AccessDeniedException("A lock is already put");
		if(documentRepository.findById(documentId).isPresent()) {
			OffsetDateTime dateTime = OffsetDateTime.now();
			Lock lock = new Lock(documentId, owner, dateTime);
			return lockRepository.save(lock);
		}
		else throw new NoSuchElementException();
	}

	public void deleteDocumentLock(Integer documentId, String userName) {
		Optional<Lock> lock = lockRepository.findById(documentId);
		if(lock.isPresent()) {
			if(lock.get().getOwner().equals(userName)) lockRepository.deleteById(documentId);
			else throw new AccessDeniedException("The lock was put by another user");
		}
		else throw new NoSuchElementException();
	}
}

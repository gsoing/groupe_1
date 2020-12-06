package com.episen.ing3.tpmra.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	public Optional<Lock> getDocumentLock(Integer documentId) {
		return lockRepository.findById(documentId);

	}

	/*
	 * TODO: treat the case were the lock already exists
	 */
	public Lock putDocumentLock(Integer documentId, String owner) {
		if(documentRepository.findById(documentId).isPresent()) {
			OffsetDateTime dateTime = OffsetDateTime.now();
			Lock lock = new Lock(documentId, owner, dateTime);
			return lockRepository.save(lock);
		}
		return null;
	}

	public HttpStatus deleteDocumentLock(Integer documentId, String userName) {
		Optional<Lock> lock = lockRepository.findById(documentId);
		if(lock.isPresent()) {
			if(lock.get().getOwner().equals(userName)) {
				lockRepository.deleteById(documentId);
				if(getDocumentLock(documentId).isPresent())
					return HttpStatus.INTERNAL_SERVER_ERROR;
				else
					return HttpStatus.NO_CONTENT;
			}
			else {
				return HttpStatus.UNAUTHORIZED;
			}
		}
		else {
			return HttpStatus.NOT_FOUND;
		}
	}
}

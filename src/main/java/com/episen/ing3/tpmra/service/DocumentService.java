package com.episen.ing3.tpmra.service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.Document.StatusEnum;
import com.episen.ing3.tpmra.model.DocumentSummary;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.model.Lock;
import com.episen.ing3.tpmra.repository.DocumentRepository;
import com.episen.ing3.tpmra.repository.LockRepository;

@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	LockRepository lockRepository;

	public DocumentsList getAllDocuments(@Valid Integer page, @Valid Integer pageSize) {
		Page<Document> pageDocuments = documentRepository.findAll(PageRequest.of(page, pageSize));
		List<Document> listDocuments = pageDocuments.getContent();
		DocumentsList list = new DocumentsList(page,pageSize);
		for(Document document: listDocuments) {
			list.addDataItem(new DocumentSummary(document.getDocumentId(), document.getCreated(), document.getUpdated(), document.getTitle()));
		}
		return list;
	}

	public DocumentsList createDocument(@Valid Document body, String userName) {
		body.setStatus(StatusEnum.CREATED);
		OffsetDateTime dateTime = OffsetDateTime.now();
		body.setCreated(dateTime);
		body.setUpdated(dateTime);
		body.setCreator(userName);
		body.setEditor(userName);
		Document documentCreated = documentRepository.save(body);
		DocumentsList list = new DocumentsList(0,1);
		list.addDataItem(new DocumentSummary(documentCreated.getDocumentId(), documentCreated.getCreated(), documentCreated.getUpdated(), documentCreated.getTitle()));
		return list;
	}

	public Document getSingleDocument(Integer documentId) {
		Optional<Document> document = documentRepository.findById(documentId);
		if(!document.isPresent()) throw new NoSuchElementException();
		return document.get();
	}

	public Document updateDocument(Integer documentId, Document document, String versionETag, String userName, Boolean isRelecteur) {
		/* Checking if exists */
		Optional<Document> currentDoc = documentRepository.findById(documentId);
		if(!currentDoc.isPresent())
			throw new NoSuchElementException();
		/* Checking pessimist lock */
		Optional<Lock> lock = lockRepository.findById(documentId);
		if(lock.isPresent() && !lock.get().getOwner().equals(userName))
			throw new AccessDeniedException("A lock was put by another user"); 
		/* Checking optimist lock */
		if(!currentDoc.get().getVersion().equals(versionETag))
			throw new OptimisticLockException();
		/* Checking permission if doc is validated */
		if(currentDoc.get().getStatus().equals(StatusEnum.VALIDATED) && !isRelecteur)
			throw new AccessDeniedException("The doc is in 'validate' state and can only be modify by relecteur");  
		OffsetDateTime dateTime = OffsetDateTime.now();
		currentDoc.get().setUpdated(dateTime);
		currentDoc.get().setEditor(userName);
		currentDoc.get().setTitle(document.getTitle());
		currentDoc.get().setBody(document.getBody());
		return documentRepository.save(currentDoc.get());
	}

	public Document updateDocumentStatus(Integer documentId, StatusEnum status) {
		Optional<Document> doc= documentRepository.findById(documentId);
		if(doc.isPresent()) {
			doc.get().setStatus(status);
			//documentRepository.deleteById(documentId);
			return documentRepository.save(doc.get());
		}
		else throw new NoSuchElementException();
	}


}


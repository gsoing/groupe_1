package com.episen.ing3.tpmra.service;
import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.exception.BadRequestException;
import com.episen.ing3.tpmra.exception.ConflictException;
import com.episen.ing3.tpmra.exception.ForbiddenException;
import com.episen.ing3.tpmra.exception.NotFoundException;
import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.Document.StatusEnum;
import com.episen.ing3.tpmra.model.DocumentSummary;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.repository.DocumentRepository;
import com.episen.ing3.tpmra.repository.LockRepository;

/**
 * Vu que le verrou n'existe pas sans document vous auriez pu merger le class DocumentService et LockService
 */
@Service
public class DocumentService {

	/**
	 * on n'utilise plus autowired mais l'injection par contstructeur
	 */
	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	LockRepository lockRepository;

	public DocumentsList getAllDocuments(@Valid Integer page, @Valid Integer pageSize) {
		if(page<0 || pageSize <=0 || pageSize>20) throw BadRequestException.DEFAULT;
		Page<Document> pageDocuments = documentRepository.findAll(PageRequest.of(page, pageSize));
		List<Document> listDocuments = pageDocuments.getContent();
		DocumentsList list = new DocumentsList(page,pageSize);

		// En java 8 il existe map() qui permet de convertir les objets d'une liste
		for(Document document: listDocuments) {
			list.addDataItem(new DocumentSummary(document.getDocumentId(), document.getCreated(), document.getUpdated(), document.getTitle()));
		}
		return list;
	}

	/**
	 * Je ne comprends pas l'intéret de la liste ici, surtout que le documentSummary ne retourne qu'une partie des informations
	 */
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
		return documentRepository.findById(documentId).orElseThrow(() -> NotFoundException.DEFAULT);
	}

	/**
	 * Spring data permet de gérer l'optimistic lock, mais ok
	 */
	public Document updateDocument(Integer documentId, Document document, String versionETag, String userName, Boolean isRelecteur) {
		/* Checking if exists */
		// Vous auriez pu utiliser la méthode au dessus pour factoriser Document savedDocument = this.getSingleDocument(documentId);
		Document savedDocument = documentRepository.findById(documentId).orElseThrow(() -> NotFoundException.DEFAULT);
		/* Checking pessimist lock */
		lockRepository.findById(documentId).ifPresent(lock -> { if (!lock.getOwner().equals(userName)) throw ForbiddenException.DEFAULT ; });;
		/* Checking optimist lock */
		if(!savedDocument.getVersion().equals(versionETag)) throw ConflictException.DEFAULT;
		/* Checking permission if doc is validated */
		if(savedDocument.getStatus().equals(StatusEnum.VALIDATED) && !isRelecteur) throw ForbiddenException.DEFAULT;
		OffsetDateTime dateTime = OffsetDateTime.now();
		savedDocument.setUpdated(dateTime);
		savedDocument.setEditor(userName);
		savedDocument.setTitle(document.getTitle());
		savedDocument.setBody(document.getBody());
		return documentRepository.save(savedDocument);
	}

	public Document updateDocumentStatus(Integer documentId, StatusEnum status) {
		Document document = documentRepository.findById(documentId).orElseThrow(() -> NotFoundException.DEFAULT);
		document.setStatus(status);
		return documentRepository.save(document);
	}


}


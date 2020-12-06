package com.episen.ing3.tpmra.service;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.Document.StatusEnum;
import com.episen.ing3.tpmra.model.DocumentSummary;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.repository.DocumentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;

	public DocumentsList getAllDocuments(@Valid Integer page, @Valid Integer pageSize) {
		Page<Document> pageDocuments = documentRepository.findAll(PageRequest.of(page, pageSize));
		log.info(">>DEBUG : Number of elements : " + pageDocuments.getSize());
		List<Document> listDocuments = pageDocuments.getContent();
		DocumentsList list = new DocumentsList(page,pageSize);
		for(Document document: listDocuments) {
			list.addDataItem(new DocumentSummary(document.getDocumentId(), document.getCreated(), document.getUpdated(), document.getTitle()));
		}
		return list;
	}

	public DocumentsList createDocument(@Valid Document body) {
		Document documentCreated = documentRepository.save(body);
		DocumentsList list = new DocumentsList(0,1);
		list.addDataItem(new DocumentSummary(documentCreated.getDocumentId(), documentCreated.getCreated(), documentCreated.getUpdated(), documentCreated.getTitle()));
		return list;
	}

	public Optional<Document> getSingleDocument(Integer documentId) {
		return documentRepository.findById(documentId);

	}

	public Document updateDocument(Document document) {
		return documentRepository.save(document);
	}

	public Document updateDocumentStatus(Integer documentId, StatusEnum status) {
		Optional<Document> doc= documentRepository.findById(documentId);
		if(doc.isPresent()) {
			doc.get().setStatus(status);
			documentRepository.deleteById(documentId);
			return documentRepository.save(doc.get());
		}
		return null;
	}


}


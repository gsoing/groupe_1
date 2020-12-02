package com.episen.ing3.tpmra.service;
import java.util.ArrayList;
import java.util.Optional;

import javax.net.ssl.SSLEngineResult.Status;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.Document.StatusEnum;
import com.episen.ing3.tpmra.model.DocumentSummary;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.repository.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;

	public DocumentsList getAllDocuments(@Valid Integer page, @Valid Integer pageSize) {
		@SuppressWarnings("unchecked")
		ArrayList<Document> listDocument = (ArrayList<Document>) documentRepository.findAll(PageRequest.of(page, pageSize));
		DocumentsList list = new DocumentsList();
		for(Document document: listDocument) {
			list.addDataItem(new DocumentSummary(document.getDocumentId(), document.getCreated(), document.getUpdated(), document.getTitle()));
		}
		return list;
	}

	public DocumentsList createDocument(@Valid Document body) {
		Document documentCreated = documentRepository.save(body);
		DocumentsList list = new DocumentsList();
		list.addDataItem(new DocumentSummary(documentCreated.getDocumentId(), documentCreated.getCreated(), documentCreated.getUpdated(), documentCreated.getTitle()));
		return list;
	}

	public Optional<Document> getSingleDocument(String documentId) {
		return documentRepository.findById(documentId);

	}

	public Document updateDocument(Document document) {
		return documentRepository.save(document);
	}

	public Document updateDocumentStatus(String documentId, StatusEnum status) {
		Optional<Document> doc= documentRepository.findById(documentId);
		if(doc.isPresent()) {
			doc.get().setStatus(status);
			documentRepository.deleteById(documentId);
			return documentRepository.save(doc.get());
		}
		return null;
	}

}


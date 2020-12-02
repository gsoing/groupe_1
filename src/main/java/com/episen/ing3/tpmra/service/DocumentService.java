package com.episen.ing3.tpmra.service;

import java.awt.List;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.episen.ing3.tpmra.model.Document;
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
		// TODO Auto-generated method stub
		return null;
	}

	public Document getSingleDocument(String documentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Document updateDocument(String documentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Document updateDocumentStatus(String documentId, String status) {
		// Status devrait être converti en une enum... Bon courage
		// TODO Auto-generated method stub
		return null;
	}

}


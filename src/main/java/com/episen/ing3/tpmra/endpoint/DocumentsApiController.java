package com.episen.ing3.tpmra.endpoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.model.Lock;
import com.episen.ing3.tpmra.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DocumentsApiController {

	@Autowired
    private ObjectMapper objectMapper;

	@Autowired
    private HttpServletRequest request;
	
	@Autowired
    private DocumentService documentService;

	/*
	 * GET /documents
	 */
	@GetMapping("/documents")
	public ResponseEntity<DocumentsList> documentsGet(@Valid @RequestParam(value = "page", required = false) Integer page, @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		log.info("ducmentsGet called");
		String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
        	try {
        		DocumentsList list = documentService.getAllDocuments(page, pageSize);
        		if(list==null)
        			return new ResponseEntity<DocumentsList>(HttpStatus.NOT_FOUND);
        		else
        			return new ResponseEntity<DocumentsList>(list,HttpStatus.OK);
        	}catch(Exception e) { return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);}
        }
        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	
	
	
	
	
	
    public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Document>(objectMapper.readValue("{\n  \"editor\" : \"editor\",\n  \"creator\" : \"creator\",\n  \"body\" : \"body\",\n  \"status\" : \"CREATED\"\n}", Document.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockGet( @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Lock>(objectMapper.readValue("{\n  \"owner\" : \"owner\",\n  \"created\" : \"2000-01-23T04:56:07.000+00:00\"\n}", Lock.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockPut(@PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Lock>(objectMapper.readValue("{\n  \"owner\" : \"owner\",\n  \"created\" : \"2000-01-23T04:56:07.000+00:00\"\n}", Lock.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Document> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @Valid @RequestBody Document body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Document>(objectMapper.readValue("{\n  \"editor\" : \"editor\",\n  \"creator\" : \"creator\",\n  \"body\" : \"body\",\n  \"status\" : \"CREATED\"\n}", Document.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @Valid @RequestBody String body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DocumentsList> documentsPost(@Valid @RequestBody Document body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<DocumentsList>(objectMapper.readValue("{\n  \"data\" : [ {\n    \"created\" : \"2000-01-23T04:56:07.000+00:00\",\n    \"documentId\" : \"documentId\",\n    \"title\" : \"title\",\n    \"updated\" : \"2000-01-23T04:56:07.000+00:00\"\n  }, {\n    \"created\" : \"2000-01-23T04:56:07.000+00:00\",\n    \"documentId\" : \"documentId\",\n    \"title\" : \"title\",\n    \"updated\" : \"2000-01-23T04:56:07.000+00:00\"\n  } ]\n}", DocumentsList.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

}

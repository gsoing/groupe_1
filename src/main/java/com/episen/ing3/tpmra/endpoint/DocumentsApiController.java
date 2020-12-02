package com.episen.ing3.tpmra.endpoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		log.info("GET /documents : documentsGet called with values page (" + page + "), pageSize(" + pageSize + ")");
		String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
        	try {
        		DocumentsList list = documentService.getAllDocuments(page, pageSize);
        		log.info("GET /documents : returning the following list " + list);
        		if(list==null)
        			return new ResponseEntity<DocumentsList>(HttpStatus.NOT_FOUND);
        		else
        			return new ResponseEntity<DocumentsList>(list,HttpStatus.OK);
        	}catch(Exception e) { 
        		log.error("GET /documents : An error occured " + e.getMessage());
        		e.printStackTrace();
        		return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        }
        log.info("GET /documents : attribute accept wasn't set to application/json");
        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	/*
	 * POST /documents
	 */
	@PostMapping("/documents")
    public ResponseEntity<DocumentsList> documentsPost(@Valid @RequestBody Document body) {
		log.info("POST /documents : documentsPost called with document body '" + body + "'");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	DocumentsList list = documentService.createDocument(body);
            	log.info("POST /documents : returning the following list " + list);
            	if(list==null)
        			return new ResponseEntity<DocumentsList>(HttpStatus.BAD_REQUEST);
        		else
        			return new ResponseEntity<DocumentsList>(list,HttpStatus.CREATED);
            } catch (Exception e) {
            	log.error("POST /documents : An error occured " + e.getMessage());
            	e.printStackTrace();
            	return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        log.info("POST /documents : attribute accept wasn't set to application/json");
        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }
	
	/*
	 * GET /documents/{documentId}
	 */
    public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") String documentId) {
    	log.info("GET /documents/{documentId} : documentsDocumentIdGet called with document id '" + documentId + "'");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	Document document = documentService.getSingleDocument(documentId);
            	log.info("GET /documents/{documentId} : returning the following document " + document);
            	if(document==null)
        			return new ResponseEntity<Document>(HttpStatus.NOT_FOUND);
        		else
        			return new ResponseEntity<Document>(document,HttpStatus.OK);
            } catch (Exception e) {
            	log.error("GET /documents/{documentId} : An error occured " + e.getMessage());
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        log.info("GET /documents/{documentId} : attribute accept wasn't set to application/json");
        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

	/*
	 * PUT /documents/{documentId}
	 */
    public ResponseEntity<Document> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @Valid @RequestBody Document body) {
    	log.info("PUT /documents/{documentId} : documentsDocumentIdPut called with document id '" + documentId + "' and body '" + body + "'");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	Document document = documentService.updateDocument(documentId);
            	log.info("PUT /documents/{documentId} : returning the following document " + document);
            	if(document==null)
        			return new ResponseEntity<Document>(HttpStatus.NOT_FOUND);
        		else
        			return new ResponseEntity<Document>(document,HttpStatus.OK);
            } catch (IOException e) {
            	log.error("PUT /documents/{documentId} : An error occured " + e.getMessage());
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        log.info("PUT /documents/{documentId} : attribute accept wasn't set to application/json");
        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    /*
     * PUT /documents/{documentId}/status:
     */
    public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @Valid @RequestBody String body) {
    	log.info("PUT /documents/{documentId}/status : documentsDocumentIdStatusPut called with document id '" + documentId + "' and body '" + body + "'");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	Document document = documentService.updateDocumentStatus(documentId,body);
            	log.info("PUT /documents/{documentId}/status : returning the following document " + document);
            	if(document==null)
        			return new ResponseEntity<Document>(HttpStatus.NOT_FOUND);
        		else
        			return new ResponseEntity<Document>(document,HttpStatus.OK);
            } catch (IOException e) {
            	log.error("PUT /documents/{documentId}/status: An error occured " + e.getMessage());
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        log.info("PUT /documents/{documentId}/status : attribute accept wasn't set to application/json");
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

}

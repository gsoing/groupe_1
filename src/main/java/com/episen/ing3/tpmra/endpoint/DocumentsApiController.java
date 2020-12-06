package com.episen.ing3.tpmra.endpoint;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.Document.StatusEnum;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.model.Lock;
import com.episen.ing3.tpmra.service.DocumentService;
import com.episen.ing3.tpmra.service.LockService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DocumentsApiController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private LockService lockService;

	/*
	 * GET /documents
	 */
	@GetMapping("/documents")
	@RolesAllowed(value = { "REDACTEUR","RELECTEUR" })
	public ResponseEntity<DocumentsList> documentsGet(@Valid @RequestParam(value = "page", required = false) Integer page, @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		log.info("GET /documents : documentsGet called with values page (" + page + "), pageSize(" + pageSize + ")");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				// Control arguments
				if(page == null) page=0;
				if(pageSize == null || pageSize>=20) {
					pageSize=10;
				}
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
	@RolesAllowed(value = { "REDACTEUR" })
	public ResponseEntity<DocumentsList> documentsPost(Principal principal, @Valid @RequestBody Document body) {
		log.info("POST /documents : documentsPost called with document body '" + body + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				DocumentsList list = documentService.createDocument(body,principal.getName());
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
	@GetMapping("/documents/{documentId}")
	@RolesAllowed(value = { "REDACTEUR","RELECTEUR" })
	public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") Integer documentId) {
		log.info("GET /documents/{documentId} : documentsDocumentIdGet called with document id '" + documentId + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				Optional<Document> optionalDocument = documentService.getSingleDocument(documentId);
				log.info("GET /documents/{documentId} : is the document present? " + optionalDocument.isPresent());
				if(!optionalDocument.isPresent())
					return new ResponseEntity<Document>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<Document>(optionalDocument.get(),HttpStatus.OK);
			} catch (Exception e) {
				log.error("GET /documents/{documentId} : An error occured " + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		log.info("GET /documents/{documentId} : attribute accept wasn't set to application/json");
		return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * PUT /documents/{documentId}
	 */
	@PutMapping("/documents/{documentId}")
	@RolesAllowed(value = { "REDACTEUR","RELECTEUR" })
	public ResponseEntity<Document> documentsDocumentIdPut(Principal principal, @PathVariable("documentId") Integer documentId, @Valid @RequestBody Document body) {
		log.info("PUT /documents/{documentId} : documentsDocumentIdPut called with document id '" + documentId + "' and body '" + body + "'");
		/* Checking if its role is RELECTEUR */
		Boolean relecteur = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("RELECTEUR")))
			relecteur=true;
		/* Main treatment */
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				Document document = documentService.updateDocument(documentId, body,principal.getName(),relecteur);
				log.info("PUT /documents/{documentId} : returning the following document: " + document);
				if(document==null)
					return new ResponseEntity<Document>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<Document>(document,HttpStatus.OK);
			} catch (Exception e) {
				log.error("PUT /documents/{documentId} : An error occured " + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		log.info("PUT /documents/{documentId} : attribute accept wasn't set to application/json");
		return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * PUT /documents/{documentId}/status:
	 */
	@PutMapping("/documents/{documentId}/status")
	@RolesAllowed(value = { "RELECTEUR" })
	public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") Integer documentId, @Valid @RequestBody String body) {
		log.info("PUT /documents/{documentId}/status : documentsDocumentIdStatusPut called with document id '" + documentId + "' and body '" + body + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				StatusEnum status = StatusEnum.fromValue(body);
				if(status==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				Document document = documentService.updateDocumentStatus(documentId, status);
				log.info("PUT /documents/{documentId}/status : the following document was returned: " + document);
				if(document==null)
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				log.error("PUT /documents/{documentId}/status: An error occured " + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		log.info("PUT /documents/{documentId}/status : attribute accept wasn't set to application/json");
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * GET /documents/{documentId}/lock
	 */
	@GetMapping("/documents/{documentId}/lock")
	@RolesAllowed(value = { "REDACTEUR","RELECTEUR" })
	public ResponseEntity<Lock> documentsDocumentIdLockGet( @PathVariable("documentId") Integer documentId) {
		log.info("GET /documents/{documentId}/lock : documentsDocumentIdLockGet called with document id '" + documentId + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				Optional<Lock> optionalLock = lockService.getDocumentLock(documentId);
				log.info("GET /documents/{documentId}/lock : is the lock present? : " + optionalLock.isPresent());
				if(!optionalLock.isPresent())
					return new ResponseEntity<Lock>(HttpStatus.NO_CONTENT);
				else
					return new ResponseEntity<Lock>(optionalLock.get(),HttpStatus.OK);
			} catch (Exception e) {
				log.error("GET /documents/{documentId}/lock : An error occured " + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		log.info("GET /documents/{documentId}/lock : attribute accept wasn't set to application/json");
		return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * PUT /documents/{documentId}/lock
	 */
	@PutMapping("/documents/{documentId}/lock")
	@RolesAllowed(value = { "REDACTEUR","RELECTEUR" })
	public ResponseEntity<Lock> documentsDocumentIdLockPut(Principal principal, @PathVariable("documentId") Integer documentId) {
		String accept = request.getHeader("Accept");
		log.info("PUT /documents/{documentId}/lock : documentsDocumentIdLockPut called with document id '" + documentId + "'");
		if (accept != null && accept.contains("application/json")) {
			try {
				Lock lock = lockService.putDocumentLock(documentId, principal.getName());
				log.info("PUT /documents/{documentId}/lock : returning the following document: " + lock);
				if(lock==null)
					return new ResponseEntity<Lock>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<Lock>(lock,HttpStatus.OK);
			} catch (Exception e) {
				log.error("PUT /documents/{documentId}/lock : An error occured " + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		log.info("PUT /documents/{documentId}/lock : attribute accept wasn't set to application/json");
		return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * DELETE /documents/{documentId}/lock
	 */
	@DeleteMapping("/documents/{documentId}/lock")
	@RolesAllowed(value = { "REDACTEUR","RELECTEUR" })
	public ResponseEntity<Void> documentsDocumentIdLockDelete(Principal principal, @PathVariable("documentId") Integer documentId) {
		String accept = request.getHeader("Accept");
		log.info("DELETE /documents/{documentId}/lock : documentsDocumentIdLockDelete called with document id '" + documentId + "'");
		if (accept != null && accept.contains("application/json")) {
			try {
				HttpStatus result = lockService.deleteDocumentLock(documentId, principal.getName());
				log.info("DELETE /documents/{documentId}/lock : Did the delete succeed? " + result);
				return new ResponseEntity<>(result);
			} catch (Exception e) {
				log.error("DELETE /documents/{documentId}/lock : An error occured " + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		log.info("DELETE /documents/{documentId}/lock : attribute accept wasn't set to application/json");
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}

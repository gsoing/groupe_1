package com.episen.ing3.tpmra.endpoint;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.episen.ing3.tpmra.model.Document;
import com.episen.ing3.tpmra.model.Document.StatusEnum;
import com.episen.ing3.tpmra.model.DocumentsList;
import com.episen.ing3.tpmra.model.Lock;
import com.episen.ing3.tpmra.service.DocumentService;
import com.episen.ing3.tpmra.service.LockService;

import lombok.extern.slf4j.Slf4j;

/**
 * L'implémentation de l'interface REST est globalement correcte.
 * Quelques remarques, HttpStatus.NOT_IMPLEMENTED indique que la méthode HTTP n'est pas implémenté, ici dans votre implémentation
 * on est plutot sur une problème de content-type donc 415 Unsupported Media Type.
 * Pour la gestion des headers on peut utiliser l'annotation @RequestHeader, pareil pour vérifier les content-type en entrée
 * on a les attributes consumes et produces.
 * De plus les objet authentification et principal peuvent être passé directement en paramètre le méthode
 */

@EnableWebMvc
@RestController
@Slf4j
public class DocumentsApiController {

	// C'est un peu osé (même si cela fonctionne) car les objets spring sont des singletons, la requête est différente pour chaque thread
	// pour plus de lisibibilité je préfère l'injecter au niveau de la méthode c'est plus lisible
	private HttpServletRequest request;

	private DocumentService documentService;

	private LockService lockService;

	public DocumentsApiController(HttpServletRequest request, DocumentService documentService, LockService lockService) {
		this.request = request;
		this.documentService = documentService;
		this.lockService = lockService;
	}

	/*
	 * GET /documents
	 */
	@GetMapping(path = "/documents", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@Secured(value = { "ROLE_REDACTEUR","ROLE_RELECTEUR" })
	// Vous auriez pu utiliser le type Pageable direcement qui regroupe tout cela
	public ResponseEntity documentsGet(@Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,
													  @Valid @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize) {
		log.info("GET /documents : documentsGet called with values page (" + page + "), pageSize(" + pageSize + ")");

		// Comme indiqué c'est plus facile d'utiliser @RequestHeader("Accept") String accept
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			DocumentsList list = documentService.getAllDocuments(page, pageSize);
			log.info("GET /documents : returning the following list " + list);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(list);
		}
		log.info("GET /documents : attribute accept wasn't set to application/json");
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * POST /documents
	 */
	@PostMapping("/documents")
	@Secured(value = { "ROLE_REDACTEUR" })
	// Pourquoi retourner une liste ici vu qu'on vient de créer un document
	public ResponseEntity<DocumentsList> documentsPost(Principal principal, @Valid @RequestBody Document body) {
		log.info("POST /documents : documentsPost called with document body '" + body + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			DocumentsList list = documentService.createDocument(body,principal.getName());
			log.info("POST /documents : returning the following list " + list);
			// Ici cela aurait été sympa de retourner l'etag en même temps
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(list);
		}
		log.info("POST /documents : attribute accept wasn't set to application/json");
		return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * GET /documents/{documentId}
	 */
	@GetMapping("/documents/{documentId}")
	@Secured(value = { "ROLE_REDACTEUR","ROLE_RELECTEUR" })
	public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") Integer documentId) {
		log.info("GET /documents/{documentId} : documentsDocumentIdGet called with document id '" + documentId + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			Document document = documentService.getSingleDocument(documentId);
			log.info("GET /documents/{documentId} : Sending version '" + document.getVersion() + "'");
			return ResponseEntity
					.status(HttpStatus.OK)
					.eTag(document.getVersion())
					.body(document);
		}
		log.info("GET /documents/{documentId} : attribute accept wasn't set to application/json");
		return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * PUT /documents/{documentId}
	 */
	@PutMapping("/documents/{documentId}")
	@Secured(value = { "ROLE_REDACTEUR","ROLE_RELECTEUR" })
	public ResponseEntity<Document> documentsDocumentIdPut(Principal principal, @RequestHeader(value = "ETag", defaultValue = "-1") String versionETag, @PathVariable("documentId") Integer documentId, @Valid @RequestBody Document body) {
		log.info("PUT /documents/{documentId} : documentsDocumentIdPut called with document id '" + documentId + "' and body '" + body + "'");
		log.info("PUT /documents/{documentId} : Version received : " + versionETag);
		/* Checking if its role is ROLE_RELECTEUR */
		Boolean isRelecteur = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RELECTEUR")))
			isRelecteur=true;
		/* Main treatment */
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			Document document = documentService.updateDocument(documentId, body, versionETag, principal.getName(), isRelecteur);
			log.info("PUT /documents/{documentId} : returning the following document: " + document);
			return ResponseEntity
					.status(HttpStatus.OK)
					.eTag(document.getVersion())
					.body(document);
		}
		log.info("PUT /documents/{documentId} : attribute accept wasn't set to application/json");
		return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * PUT /documents/{documentId}/status:
	 */
	// Vous auriez pu mettre directement le type de l'enum en entrée de la méthode, Spring fait la conversion
	@PutMapping("/documents/{documentId}/status")
	@Secured(value = { "ROLE_RELECTEUR" })
	public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") Integer documentId, @Valid @RequestBody String body) {
		log.info("PUT /documents/{documentId}/status : documentsDocumentIdStatusPut called with document id '" + documentId + "' and body '" + body + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			StatusEnum status = StatusEnum.fromValue(body);
			if(status==null) throw new IllegalArgumentException();
			Document document = documentService.updateDocumentStatus(documentId, status);
			log.info("PUT /documents/{documentId}/status : the following document was returned: " + document);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		log.info("PUT /documents/{documentId}/status : attribute accept wasn't set to application/json");
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * GET /documents/{documentId}/lock
	 */
	@GetMapping("/documents/{documentId}/lock")
	@Secured(value = { "ROLE_REDACTEUR","ROLE_RELECTEUR" })
	public ResponseEntity<Lock> documentsDocumentIdLockGet( @PathVariable("documentId") Integer documentId) {
		log.info("GET /documents/{documentId}/lock : documentsDocumentIdLockGet called with document id '" + documentId + "'");
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			Optional<Lock> optionalLock = lockService.getDocumentLock(documentId);
			log.info("GET /documents/{documentId}/lock : Is the lock present? : " + optionalLock.isPresent());
			if(optionalLock.isPresent())
				return ResponseEntity.status(HttpStatus.OK).body(optionalLock.get());
			else 
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		log.info("GET /documents/{documentId}/lock : attribute accept wasn't set to application/json");
		return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * PUT /documents/{documentId}/lock
	 */
	@PutMapping("/documents/{documentId}/lock")
	@Secured(value = { "ROLE_REDACTEUR","ROLE_RELECTEUR" })
	public ResponseEntity<Lock> documentsDocumentIdLockPut(Principal principal, @PathVariable("documentId") Integer documentId) {
		String accept = request.getHeader("Accept");
		log.info("PUT /documents/{documentId}/lock : documentsDocumentIdLockPut called with document id '" + documentId + "'");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			Lock lock = lockService.putDocumentLock(documentId, principal.getName());
			log.info("PUT /documents/{documentId}/lock : returning the following lock: " + lock);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(lock);
		}
		log.info("PUT /documents/{documentId}/lock : attribute accept wasn't set to application/json");
		return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * DELETE /documents/{documentId}/lock
	 */
	@DeleteMapping("/documents/{documentId}/lock")
	@Secured(value = { "ROLE_REDACTEUR","ROLE_RELECTEUR" })
	public ResponseEntity<Void> documentsDocumentIdLockDelete(Principal principal, @PathVariable("documentId") Integer documentId) {
		String accept = request.getHeader("Accept");
		log.info("DELETE /documents/{documentId}/lock : documentsDocumentIdLockDelete called with document id '" + documentId + "'");
		if (accept != null && accept.contains("application/json")) {
			/* Main Treatment */
			lockService.deleteDocumentLock(documentId, principal.getName());
			log.info("DELETE /documents/{documentId}/lock : The delete was successful ");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		log.info("DELETE /documents/{documentId}/lock : attribute accept wasn't set to application/json");
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}

package com.episen.ing3.tpmra.api;

import io.swagger.model.Document;
import io.swagger.model.DocumentsList;
import io.swagger.model.ErrorDefinition;
import io.swagger.model.Lock;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-12-02T13:05:00.076Z[GMT]")
@RestController
public class DocumentsApiController implements DocumentsApi {

    private static final Logger log = LoggerFactory.getLogger(DocumentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DocumentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Document> documentsDocumentIdGet(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
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

    public ResponseEntity<Void> documentsDocumentIdLockDelete(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockGet(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
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

    public ResponseEntity<Lock> documentsDocumentIdLockPut(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
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

    public ResponseEntity<Document> documentsDocumentIdPut(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Document body) {
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

    public ResponseEntity<Void> documentsDocumentIdStatusPut(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody String body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DocumentsList> documentsGet(@Parameter(in = ParameterIn.QUERY, description = "numéro de la page à retourner" ,schema=@Schema()) @Valid @RequestParam(value = "page", required = false) Integer page,@Parameter(in = ParameterIn.QUERY, description = "nombre de documents par page" ,schema=@Schema()) @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize) {
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

    public ResponseEntity<DocumentsList> documentsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Document body) {
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

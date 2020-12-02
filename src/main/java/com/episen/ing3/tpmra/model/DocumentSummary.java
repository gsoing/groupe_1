package com.episen.ing3.tpmra.model;

import java.util.Objects;

import javax.persistence.Id;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * résumé d'un document
 */
@Schema(description = "résumé d'un document")
@Validated
public class DocumentSummary   {
	
	@Id
	@JsonProperty("documentId")
	private String documentId = null;

	@JsonProperty("created")
	private OffsetDateTime created = null;

	@JsonProperty("updated")
	private OffsetDateTime updated = null;

	@JsonProperty("title")
	private String title = null;

	public DocumentSummary documentId(String documentId) {
		this.documentId = documentId;
		return this;
	}



	public DocumentSummary(String documentId, OffsetDateTime created, OffsetDateTime updated, String title) {
		super();
		this.documentId = documentId;
		this.created = created;
		this.updated = updated;
		this.title = title;
	}



	/**
	 * identifiant du document
	 * @return documentId
	 **/
	@Schema(description = "identifiant du document")

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public DocumentSummary created(OffsetDateTime created) {
		this.created = created;
		return this;
	}

	/**
	 * la date de création
	 * @return created
	 **/
	@Schema(description = "la date de création")

	@Valid
	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public DocumentSummary updated(OffsetDateTime updated) {
		this.updated = updated;
		return this;
	}

	/**
	 * date de la mise à jour
	 * @return updated
	 **/
	@Schema(description = "date de la mise à jour")

	@Valid
	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}

	public DocumentSummary title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * titre du document
	 * @return title
	 **/
	@Schema(description = "titre du document")

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DocumentSummary documentSummary = (DocumentSummary) o;
		return Objects.equals(this.documentId, documentSummary.documentId) &&
				Objects.equals(this.created, documentSummary.created) &&
				Objects.equals(this.updated, documentSummary.updated) &&
				Objects.equals(this.title, documentSummary.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(documentId, created, updated, title);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DocumentSummary {\n");

		sb.append("    documentId: ").append(toIndentedString(documentId)).append("\n");
		sb.append("    created: ").append(toIndentedString(created)).append("\n");
		sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
		sb.append("    title: ").append(toIndentedString(title)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}

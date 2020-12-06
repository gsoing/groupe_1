package com.episen.ing3.tpmra.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@Table(name="Document")
public class Document extends DocumentSummary  {

	@Column(name = "creator")
	private String creator;

	@Column(name = "editor")
	private String editor;

	@Column(name = "body")
	private String body;

	public Document(@JsonProperty("documentId") Integer documentId, @JsonProperty("created") OffsetDateTime created, @JsonProperty("updated") OffsetDateTime updated, @JsonProperty("title") String title, @JsonProperty("creator") String creator, @JsonProperty("editor") String editor, @JsonProperty("body") String body) {
		super(documentId, created, updated, title);
		this.creator=creator;
		this.editor=editor;
		this.body=body;
	}
	/*
  public Document(@JsonProperty("documentId") String documentId,@JsonProperty("created") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ") OffsetDateTime created, @JsonProperty("updated") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ") OffsetDateTime updated, @JsonProperty("title") String title) {
		super(documentId, created, updated, title);
	}*/

	public enum StatusEnum {
		CREATED("CREATED"),

		VALIDATED("VALIDATED");

		private String value;

		StatusEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static StatusEnum fromValue(String text) {
			for (StatusEnum b : StatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}
	@JsonProperty("status")
	private StatusEnum status = null;

	public Document creator(String creator) {
		this.creator = creator;
		return this;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Document document = (Document) o;
		return Objects.equals(this.creator, document.creator) &&
				Objects.equals(this.editor, document.editor) &&
				Objects.equals(this.body, document.body) &&
				Objects.equals(this.status, document.status) &&
				super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(creator, editor, body, status, super.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Document {\n");
		sb.append("    ").append(toIndentedString(super.toString())).append("\n");
		sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
		sb.append("    editor: ").append(toIndentedString(editor)).append("\n");
		sb.append("    body: ").append(toIndentedString(body)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

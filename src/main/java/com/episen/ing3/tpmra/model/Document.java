package com.episen.ing3.tpmra.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.DocumentSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * a document
 */
@Schema(description = "a document")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-12-02T13:05:00.076Z[GMT]")


public class Document extends DocumentSummary  {
  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("editor")
  private String editor = null;

  @JsonProperty("body")
  private String body = null;

  /**
   * statut du document
   */
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

  /**
   * le nom du créateur du document
   * @return creator
   **/
  @Schema(description = "le nom du créateur du document")
  
    public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Document editor(String editor) {
    this.editor = editor;
    return this;
  }

  /**
   * le nom du dernier utilisateur qui l'a mis à jour
   * @return editor
   **/
  @Schema(description = "le nom du dernier utilisateur qui l'a mis à jour")
  
    public String getEditor() {
    return editor;
  }

  public void setEditor(String editor) {
    this.editor = editor;
  }

  public Document body(String body) {
    this.body = body;
    return this;
  }

  /**
   * le texte du document
   * @return body
   **/
  @Schema(description = "le texte du document")
  
    public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Document status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * statut du document
   * @return status
   **/
  @Schema(description = "statut du document")
  
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

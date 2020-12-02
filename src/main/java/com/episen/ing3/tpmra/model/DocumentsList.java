package com.episen.ing3.tpmra.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * la liste des résultats d&#x27;une recherche de document
 */
@Schema(description = "la liste des résultats d'une recherche de document")
@Validated
public class DocumentsList extends PageData  {
  @JsonProperty("data")
  @Valid
  private List<DocumentSummary> data = null;

  public DocumentsList data(List<DocumentSummary> data) {
    this.data = data;
    return this;
  }

  public DocumentsList addDataItem(DocumentSummary dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<DocumentSummary>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  @Schema(description = "")
      @Valid
    public List<DocumentSummary> getData() {
    return data;
  }

  public void setData(List<DocumentSummary> data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentsList documentsList = (DocumentsList) o;
    return Objects.equals(this.data, documentsList.data) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentsList {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

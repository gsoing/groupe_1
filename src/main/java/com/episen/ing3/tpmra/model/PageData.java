package com.episen.ing3.tpmra.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * objet générique pour les résultats de recherche
 */
@Schema(description = "objet générique pour les résultats de recherche")
@Validated
public class PageData   {
  @JsonProperty("page")
  private Integer page = null;

  @JsonProperty("nbElements")
  private Integer nbElements = null;

  public PageData page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * le numéro de la page courante
   * @return page
   **/
  @Schema(description = "le numéro de la page courante")
  
    public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public PageData nbElements(Integer nbElements) {
    this.nbElements = nbElements;
    return this;
  }

  /**
   * le nombre total d'element
   * @return nbElements
   **/
  @Schema(description = "le nombre total d'element")
  
    public Integer getNbElements() {
    return nbElements;
  }

  public void setNbElements(Integer nbElements) {
    this.nbElements = nbElements;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageData pageData = (PageData) o;
    return Objects.equals(this.page, pageData.page) &&
        Objects.equals(this.nbElements, pageData.nbElements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, nbElements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageData {\n");
    
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    nbElements: ").append(toIndentedString(nbElements)).append("\n");
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

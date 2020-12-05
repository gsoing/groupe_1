package com.episen.ing3.tpmra.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.threeten.bp.OffsetDateTime;

@Entity
public class Lock   {

	@Id
	private String documentId = null;

	private String owner = null;

	private OffsetDateTime created = null;

	public Lock(String documentId, String owner, OffsetDateTime created) {
		super();
		this.documentId = documentId;
		this.owner = owner;
		this.created = created;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Lock created(OffsetDateTime created) {
		this.created = created;
		return this;
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Lock lock = (Lock) o;
		return Objects.equals(this.owner, lock.owner) &&
				Objects.equals(this.created, lock.created);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Lock {\n");

		sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
		sb.append("    created: ").append(toIndentedString(created)).append("\n");
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

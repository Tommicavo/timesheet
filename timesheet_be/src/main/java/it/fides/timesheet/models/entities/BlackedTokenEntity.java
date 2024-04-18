package it.fides.timesheet.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blacklist")
public class BlackedTokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idBlackedToken;
	
	@Column(name = "token")
	private String valueBlackedToken;
	
	public BlackedTokenEntity() {}
	public BlackedTokenEntity(String valueBlackedToken ) {
		this.valueBlackedToken = valueBlackedToken;
	}

	public Long getIdBlackedToken() {
		return idBlackedToken;
	}

	public void setIdBlackedToken(Long idBlackedToken) {
		this.idBlackedToken = idBlackedToken;
	}

	public String getValueBlackedToken() {
		return valueBlackedToken;
	}

	public void setValueBlackedToken(String valueBlackedToken) {
		this.valueBlackedToken = valueBlackedToken;
	}
}

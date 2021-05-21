package main.domain;

import java.io.Serializable;

import javax.persistence.Entity;

//@Entity
public class Avaria extends Problema implements Serializable {
	private static final long serialVersionUID = 1L;

	private String imageUrl;

	public Avaria( String nome, String descricao, String imageUrl) {
		super(nome, descricao);
		this.imageUrl=imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}

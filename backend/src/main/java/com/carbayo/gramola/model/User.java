package com.carbayo.gramola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String email; // El email es la clave primaria

    @Column(nullable = false)
    private String bar; // Nombre del bar

    @Column(nullable = false)
    private String pwd; // Contraseña cifrada (idealmente)

    // Credenciales de Spotify (Obligatorias para que la gramola funcione)
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    // Token para confirmar el email
    @Column(name = "creation_token_id")
    private String creationTokenId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCreationTokenId() {
		return creationTokenId;
	}

	public void setCreationTokenId(String creationTokenId) {
		this.creationTokenId = creationTokenId;
	}

    // Getters, Setters y Constructores vacíos...
    
}

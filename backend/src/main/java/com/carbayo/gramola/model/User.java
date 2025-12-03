package com.carbayo.gramola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getSpotifyClientId() {
		return spotifyClientId;
	}

	public void setSpotifyClientId(String spotifyClientId) {
		this.spotifyClientId = spotifyClientId;
	}

	public String getSpotifyClientSecret() {
		return spotifyClientSecret;
	}

	public void setSpotifyClientSecret(String spotifyClientSecret) {
		this.spotifyClientSecret = spotifyClientSecret;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(unique = true)
    private String email;
    
    private String password; 
    
    

    @Column(name = "spotify_client_id")
    private String spotifyClientId;

    @Column(name = "spotify_client_secret")
    private String spotifyClientSecret;

    
}
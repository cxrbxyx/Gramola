package com.gramlolabe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "creation_time")
    private long creationTime;

    @Column(name = "use_time")
    private long useTime;

    @Column(name = "email", length = 255)
    private String email;

    public Token() {}

    public Token(String id, long creationTime, String email) {
        this.id = id;
        this.creationTime = creationTime;
        this.email = email;
        this.useTime = 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public long getCreationTime() { return creationTime; }
    public void setCreationTime(long creationTime) { this.creationTime = creationTime; }
    public long getUseTime() { return useTime; }
    public void setUseTime(long useTime) { this.useTime = useTime; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

package com.example.PassProtect.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "site_detail", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "site_name"})
})
public class SiteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String siteName;

    @Column(nullable = false)
    private String password;  // Encrypted password

    @Column(nullable = false, length = 24)
    private String iv;  // Random IV used for encryption

    @Column(nullable = false, length = 24)
    private String salt;  // Salt used for PBKDF2 key derivation

    @Column
    private String key;  // User-supplied key

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getIv() { return iv; }
    public void setIv(String iv) { this.iv = iv; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

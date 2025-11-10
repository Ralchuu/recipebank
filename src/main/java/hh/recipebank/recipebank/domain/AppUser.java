package hh.recipebank.recipebank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class AppUser {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Käyttäjätunnus on pakollinen")
    @Size(max = 20, message = "Käyttäjätunnus saa olla enintään 20 merkkiä")
    private String username;

    @NotBlank(message = "Salasana on pakollinen")
    @Size(min = 5, message = "Salasanan on oltava vähintään 5 merkkiä")
    @JsonIgnore
    private String password;

    @NotBlank(message = "Sähköposti on pakollinen")
    @Email(message = "Anna kelvollinen sähköpostiosoite")
    private String email;

    private String role; // "ROLE_USER" or "ROLE_ADMIN"

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    // Constructors
    public AppUser() {}

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}

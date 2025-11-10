package com.floriano.legato_api.model.User;

import com.floriano.legato_api.model.Post.Post;
import com.floriano.legato_api.model.User.AuxiliaryEntity.Artist;
import com.floriano.legato_api.model.User.AuxiliaryEntity.ConnectionRequest;
import com.floriano.legato_api.model.User.AuxiliaryEntity.ExternalLinks;
import com.floriano.legato_api.model.User.AuxiliaryEntity.Location;
import com.floriano.legato_api.model.User.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String username;
    private String displayName;

    private String profilePicture;
    private String profileBanner;

    @ElementCollection
    @CollectionTable(name = "user_photos_card", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "photo_url")
    private List<String> photosCard = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserSex sex;

    // MÚSICA E PREFERÊNCIAS
    @ElementCollection(targetClass = InstrumentList.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_instruments", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "instrument")
    private List<InstrumentList> instruments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_artists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> favoriteArtists = new ArrayList<>();

    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_genres", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "genre")
    private List<Genre> genres = new ArrayList<>();

    private String goal;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // SOCIAL

    // Usuários que seguem este usuário
    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    // Usuários que este usuário segue
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> following = new HashSet<>();

    // Conexões mútuas (amizades aceitas)
    @ManyToMany
    @JoinTable(
            name = "user_connections",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connected_user_id")
    )
    private Set<User> connections = new HashSet<>();

    // Usuários bloqueados
    @ManyToMany
    @JoinTable(
            name = "user_blocked",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id")
    )
    private Set<User> blockedUsers = new HashSet<>();

    // Pedidos de conexão enviados
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConnectionRequest> sentRequests = new ArrayList<>();

    // Pedidos de conexão recebidos
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConnectionRequest> receivedRequests = new ArrayList<>();

    // METADADOS

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // LINKS EXTERNOS

    @Embedded
    private ExternalLinks links;

    // ======== CONSTRUTOR E USERDETAILS ========

    public User(String email, String password, UserRole role, String username, String displayName) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
        this.displayName = displayName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // MÉTODOS AUXILIARES

    public void follow(User user) {
        if (user == null || user.equals(this)) return;
        if (!this.following.contains(user)) {
            this.following.add(user);
            user.getFollowers().add(this);
        }
    }

    public void unfollow(User user) {
        if (user == null || user.equals(this)) return;
        if (this.following.remove(user)) {
            user.getFollowers().remove(this);
        }
    }

    public void blockUser(User user) {
        if (user == null || user.equals(this)) return;
        this.blockedUsers.add(user);
        this.following.remove(user);
        this.followers.remove(user);
        this.connections.remove(user);
        user.getConnections().remove(this);
        user.getFollowers().remove(this);
        user.getFollowing().remove(this);
    }

    public void unblockUser(User user) {
        if (user == null) return;
        this.blockedUsers.remove(user);
    }

    public ConnectionRequest sendConnectionRequest(User receiver, String message) {
        if (receiver == null || receiver.equals(this)) return null;
        ConnectionRequest request = new ConnectionRequest();
        request.setSender(this);
        request.setReceiver(receiver);
        request.setMessage(message);
        this.sentRequests.add(request);
        receiver.getReceivedRequests().add(request);
        return request;
    }

    public void acceptConnectionRequest(ConnectionRequest request) {
        if (request == null || !this.equals(request.getReceiver())) return;
        request.setStatus(ConnectionStatus.ACCEPTED);
        User sender = request.getSender();
        this.connections.add(sender);
        sender.getConnections().add(this);
    }

    public void rejectConnectionRequest(ConnectionRequest request) {
        if (request == null || !this.equals(request.getReceiver())) return;
        request.setStatus(ConnectionStatus.REJECTED);
    }

    public void cancelConnectionRequest(ConnectionRequest request) {
        if (request == null || !this.equals(request.getSender())) return;
        request.setStatus(ConnectionStatus.CANCELLED);
    }

    public void removeConnection(User user) {
        if (user == null || user.equals(this)) return;
        this.connections.remove(user);
        user.getConnections().remove(this);
    }
}

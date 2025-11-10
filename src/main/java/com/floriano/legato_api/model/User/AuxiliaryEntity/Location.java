package com.floriano.legato_api.model.User.AuxiliaryEntity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class Location {
    private double latitude;
    private double longitude;
    private String city;
    private String country;
    @Column(name = "location_updated_at")
    private LocalDateTime updatedAt;
}

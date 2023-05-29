package by.teachmeskills.ps.model;

import java.time.LocalDateTime;

public class Merchant {
    private String id;
    private String name;
    private LocalDateTime createdAt;

    public Merchant(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "Merchant{id='" + this.id + "', name='" + this.name + "', createdAt=" + this.createdAt + "}";
    }
}

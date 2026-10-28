package cl.goldenoddjobs.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignGroupRequest {
    private String email;

    @JsonProperty("rol")
    private String role;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

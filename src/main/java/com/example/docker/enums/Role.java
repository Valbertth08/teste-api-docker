package com.example.docker.enums;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    FUNC("func");
    private String role;
    Role(String role){
        this.role=role;
    }
    public String getRole() {
        return role;
    }
}

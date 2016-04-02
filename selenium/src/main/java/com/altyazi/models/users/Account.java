package com.altyazi.models.users;

public interface Account<P extends Pool> {
    void setPool(P pool);

    void free();

    String getUsername();
}

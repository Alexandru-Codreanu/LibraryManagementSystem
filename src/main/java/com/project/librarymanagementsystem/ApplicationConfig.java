package com.project.librarymanagementsystem;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/MySQL8App"
)
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}

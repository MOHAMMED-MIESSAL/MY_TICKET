package com.projets.my_ticket.enums;

import java.util.Set;

public enum Role {
    SUPER_ADMIN((Set.of("ADD_USER", "DELETE_USER", "UPDATE_USER", "VIEW_USER", "ADD_EVENT", "DELETE_EVENT", "UPDATE_EVENT", "VIEW_EVENT", "ADD_TICKET", "DELETE_TICKET", "UPDATE_TICKET", "VIEW_TICKET"))) ,
    ADMIN((Set.of("VIEW_USER", "ADD_EVENT", "DELETE_EVENT", "UPDATE_EVENT", "VIEW_EVENT", "ADD_TICKET", "DELETE_TICKET", "UPDATE_TICKET", "VIEW_TICKET"))) ,
    ORGANIZER((Set.of("ADD_EVENT", "DELETE_EVENT", "UPDATE_EVENT", "VIEW_EVENT", "ADD_TICKET", "DELETE_TICKET", "UPDATE_TICKET", "VIEW_TICKET"))) ,
    USER((Set.of("VIEW_EVENT", "VIEW_TICKET")));

    private final Set<String> authorities;

    Role(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

}

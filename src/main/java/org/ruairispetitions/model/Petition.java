package org.ruairispetitions.model;

import java.time.LocalDateTime;
// import java.util.List;

import org.springframework.data.annotation.Id;

public record Petition(
        @Id Integer id,
        String title,
        String description,
        LocalDateTime date,
        String signatures) {

}

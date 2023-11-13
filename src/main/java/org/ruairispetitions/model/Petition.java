/**
 * The data model for the petitions application
 * 
 * @author ruairin
 *
 */

package org.ruairispetitions.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Petition {

    // @id annotation is required for Spring Data
    // as the key for the records
    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String signatures;

    public Petition() {
        this.id = null;
        this.title = null;
        this.description = null;
        this.date = null;
        this.signatures = new String();
    }

    public Petition(Integer id, String title,
            String description, LocalDateTime date, String signatures) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.signatures = signatures;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSignatures() {
        return this.signatures;
    }

    public void setSignatures(String signatures) {
        this.signatures = signatures;
    }

    public void appendSignatures(String signatures) {
        if (!this.signatures.isEmpty()) {
            this.signatures = this.signatures + ", ";
        }
        this.signatures = this.signatures + signatures;
    }

}

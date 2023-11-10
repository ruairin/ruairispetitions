package org.ruairispetitions.model;

import org.springframework.data.annotation.Id;

public class Petition {

    @Id 
    private Integer id;
    private String title;
    private String description;
    private String date;
    private String signatures;
    

    public Petition() {
        this.id = null;
        this.title = null;
        this.description = null;
        this.date = null;
        this.signatures = null;
    }

    public Petition(Integer id, String title, String description, String date, String signatures) {
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSignatures() {
        return this.signatures;
    }

    public void setSignatures(String signatures) {
        this.signatures = signatures;
    }

    public void appendSignatures(String signatures) {
        this.signatures = this.signatures + ", " + signatures;
    }


}

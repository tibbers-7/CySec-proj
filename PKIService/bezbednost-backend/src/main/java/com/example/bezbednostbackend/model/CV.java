package com.example.bezbednostbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cvs")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    private Integer engineerID;
    private String docName;
    private String docType;
    @Lob
    private byte[] data;

    public CV(Integer engineerID, String docName, String docType, byte[] data) {
        this.engineerID = engineerID;
        this.docName = docName;
        this.docType = docType;
        this.data = data;
    }
}

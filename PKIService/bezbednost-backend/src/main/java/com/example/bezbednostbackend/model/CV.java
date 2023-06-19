package com.example.bezbednostbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cvs")
@NamedQuery(name="findCVByEngineer",query="SELECT cv FROM CV cv WHERE cv.engineerID=:engineerId")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    private Integer engineerID;
    private String docName;
    private String docType;
    @Lob
    @Column(name = "data",length = 1000)
    private byte[] docData;

    public CV(Integer engineerID, String docName, String docType, byte[] data) {
        this.engineerID = engineerID;
        this.docName = docName;
        this.docType = docType;
        this.docData = data;
    }
}

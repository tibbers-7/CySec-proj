package com.example.bezbednostbackend.model.token;

import com.example.bezbednostbackend.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="verification_tokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    private String username;

    private LocalDateTime expiryDate;
    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        Date expiry= new Date(cal.getTime().getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(expiry.toInstant(),
                ZoneId.systemDefault());
        return ldt;
    }

    public VerificationToken(String username,String token){
        this.expiryDate=calculateExpiryDate(1440);
        this.username=username;
        this.token=token;
    }
}

package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public String registerUser(RegistrationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(!usernameIsValidForRegistration(dto.getUsername()) ) return "vec postoji ili je blokiran";
        if(!workTitleIsValid(dto.getWorkTitle())) return "nepravilno nazvana pozicija u firmi";
        String hashedPassword = hashPassword(dto.getPassword());
        User user = new User(dto.getName(), dto.getSurname(), dto.getUsername(), hashedPassword, dto.getAddress(), dto.getPhoneNumber(), dto.getWorkTitle(), false);
        //mora se proveriti da li ce se sacuvati preko postojeceg kad mu istekne blok, to nekako da se odradi
        userRepository.save(user);
        return "uspehh";
    }

    public boolean usernameIsValidForRegistration(String username) {
//ovo proveriti sta ce da vrati ako ne postoji
        //prvo proverimo da li postoji, ako ne odmah true
        User user = userRepository.findByUsername(username);
        if (user != null) return true;
        //ako je korisnik blokiran ili vec postoji aktivan nalog s tim usernameom nije validan
        if(userIsBlocked(user) || user.isActive) return false;
        return true;
    }

    public boolean userIsBlocked(User user){
        //ako je istek blokiranja u proslosti, vracamo false jer user nije blokiran
        //stavila sam da se onemoguci naredna 3 dana registracija ako je blokirn
        if(user.timeOfBlocking.plusDays(3).isBefore(LocalDateTime.now())) return false;
        return true;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //koristimo PBKDF2 hashing algoritam
        //kreiramo salt za hashovanje
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        //instanciramo, 65536 je strength, tj koliko iteracija traje algoritam
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash, StandardCharsets.UTF_8);
    }

    public boolean workTitleIsValid(String workTitle){
        return (workTitle.equals("HUMAN_RESOURCES_MANAGER")|| workTitle.equals("ENGINEER") || workTitle.equals("PROJECT_MANAGER"));
    }

    public User getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        return null;
    }

    public void denyRegistrationOfUser(User user){
        //mora se poslati mejl korisniku sa detaljima odbijanja, prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
        //ovde sad podesavamo u useru vreme blokirajna da bude now tako da kad pokusa da se registruje opet nece moci u naredna tri dana
        user.setTimeOfBlocking(LocalDateTime.now());
        //treba da se sacuva korisnik tako u bazu kao za apdejt


    }


}

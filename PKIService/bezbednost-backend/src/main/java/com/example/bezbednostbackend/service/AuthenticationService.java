package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.RegistrationResponseDTO;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.RegistrationRequestRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;


    public RegistrationResponseDTO makeRegistrationRequest(RegistrationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {

        RegistrationResponseDTO validity = requestIsValid(dto.getUsername(), dto.getWorkTitle());
        if(!validity.isValid()) return validity;
        String hashedPassword = hashPassword(dto.getPassword());
        RegistrationRequest request = new RegistrationRequest(dto.getName(), dto.getSurname(), dto.getUsername(), hashedPassword, dto.getAddress(), dto.getPhoneNumber(), dto.getWorkTitle(), LocalDateTime.now(), false, false);
        registrationRequestRepository.save(request);
        return new RegistrationResponseDTO("Request sent!", true);
    }

    public boolean usernameExists(String username) {
        //proveriti usere, ne sme da postoji s tim usernameom
        User user = userRepository.findByUsername(username);
        if (user != null) return true;
        return false;
    }

    public RegistrationResponseDTO requestIsValid(String username, String workTitle){

        if(usernameExists(username)) return new RegistrationResponseDTO("Username is already used!", false);
        if(!workTitleIsValid(workTitle)) return new RegistrationResponseDTO("Invalid work title entered!", false);
        //ovo zapravo treba da vraca listu requestova
        RegistrationRequest request = registrationRequestRepository.findByUsername(username);
        //ako ne postoji vec zahtev za ovog korisnika superiska
        if(request == null) return new RegistrationResponseDTO("Username is valid for use!", true);
        //true ce biti ako zahtev postoji, i isResolved = false, znaci da je na cekanju
        else if(!request.isResolved()) new RegistrationResponseDTO("A request with this username already exists!", false);
        //isto ce biti true ako resolved = true i isCancelled = true
        else if(request.isCancelled() && request.getRequestCreated().plusDays(3).isAfter(LocalDateTime.now())) new RegistrationResponseDTO("This registration request has been blocked! Try again in a few days", false);
        return new RegistrationResponseDTO("NEZ", true);
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

    public void cancelRegistrationRequest(RegistrationCancellationDTO dto){
        //mora se poslati mejl korisniku sa detaljima odbijanja, prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
        Optional<RegistrationRequest> request = registrationRequestRepository.findById(dto.getIdOfRequest());
        if(request.isEmpty()) return;
        request.get().setCancelled(true);
        request.get().setResolved(true);
        registrationRequestRepository.save(request.get());
    }

    //novi dto za approval
    public void approveRegistrationRequest(RegistrationCancellationDTO dto){
        //mora se poslati mejl korisniku sa detaljima odbijanja, prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
        Optional<RegistrationRequest> request = registrationRequestRepository.findById(dto.getIdOfRequest());
        if(request.isEmpty()) return;
        request.get().setCancelled(true);
        request.get().setResolved(true);
        registrationRequestRepository.save(request.get());
    }


}

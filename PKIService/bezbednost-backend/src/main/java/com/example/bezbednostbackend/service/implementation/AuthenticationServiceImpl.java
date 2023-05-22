package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.RegistrationResponseDTO;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.RegistrationRequestRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.AuthenticationService;
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
import java.util.List;
import java.util.Optional;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    public AuthenticationServiceImpl(RegistrationRequestRepository registrationRequestRepository, UserRepository userRepository) {
        this.registrationRequestRepository = registrationRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResponseDTO makeRegistrationRequest(RegistrationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {

        RegistrationResponseDTO validity = requestIsValid(dto.getUsername(), dto.getWorkTitle());
        if(!validity.isValid()) return validity;
        String hashedPassword = hashPassword(dto.getPassword());
        RegistrationRequest request = new RegistrationRequest(dto.getName(), dto.getSurname(), dto.getUsername(), hashedPassword, dto.getAddress(), dto.getPhoneNumber(), dto.getWorkTitle(), LocalDateTime.now(), false, false);
        registrationRequestRepository.save(request);
        return new RegistrationResponseDTO("Request sent!", true);
    }

    @Override
    public boolean usernameExists(String username) {
        //proveriti usere, ne sme da postoji s tim usernameom
        User user = userRepository.findByUsername(username);
        if (user != null) return true;
        return false;
    }
    @Override
    public RegistrationResponseDTO requestIsValid(String username, String workTitle){

        if(usernameExists(username)) return new RegistrationResponseDTO("Username is already used!", false);
        if(!workTitleIsValid(workTitle)) return new RegistrationResponseDTO("Invalid work title entered!", false);
        List<RegistrationRequest> requests = registrationRequestRepository.findByUsername(username);
        if(requests.isEmpty()) return new RegistrationResponseDTO("Username is valid for use!", true);

        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO("Sve ok", true);
        for (RegistrationRequest request: requests
             ) {
               //true ce biti ako zahtev postoji, i isResolved = false, znaci da je na cekanju
            if(!request.isResolved()) {
                responseDTO = new RegistrationResponseDTO("A request with this username already exists!", false);
                break;
            }
            else if(request.isCancelled() && request.getRequestCreated().plusDays(3).isAfter(LocalDateTime.now())){
                responseDTO = new RegistrationResponseDTO("This registration request has been blocked! Try again in a few days", false);
                break;
            }
            }
        return responseDTO;

    }
    @Override
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
    @Override
    public boolean workTitleIsValid(String workTitle){
        return (workTitle.equals("HUMAN_RESOURCES_MANAGER")|| workTitle.equals("ENGINEER") || workTitle.equals("PROJECT_MANAGER"));
    }
    @Override
    public void cancelRegistrationRequest(RegistrationCancellationDTO dto){
        //mora se poslati mejl korisniku sa detaljima odbijanja, prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
        Optional<RegistrationRequest> request = registrationRequestRepository.findById(dto.getIdOfRequest());
        if(request.isEmpty()) return;
        request.get().setCancelled(true);
        request.get().setResolved(true);
        registrationRequestRepository.save(request.get());
    }
    @Override
    //novi dto za approval
    public void approveRegistrationRequest(RegistrationCancellationDTO dto){
        //mora se poslati mejl korisniku sa detaljima odbijanja, prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
        Optional<RegistrationRequest> request = registrationRequestRepository.findById(dto.getIdOfRequest());
        //kad se odobri mora se dodati u usere
        if(request.isEmpty()) return;
        request.get().setCancelled(true);
        request.get().setResolved(true);
        registrationRequestRepository.save(request.get());
    }


}

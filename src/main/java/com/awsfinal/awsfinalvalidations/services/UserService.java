package com.awsfinal.awsfinalvalidations.services;

import com.awsfinal.awsfinalvalidations.DTOs.UserDTO;
import com.awsfinal.awsfinalvalidations.exceptions.InvalidEmailException;
import com.awsfinal.awsfinalvalidations.exceptions.InvalidPhoneNumberException;
import com.awsfinal.awsfinalvalidations.utils.UserFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserFormatter userFormatter;

    @Autowired
    public UserService(UserFormatter userFormatter) {
        this.userFormatter = userFormatter;
    }

    public ResponseEntity<UserDTO> validate(UserDTO userDTO){
        if(validateUser(userDTO)){
            return ResponseEntity.ok(formatedUserDTO(userDTO));
        }else{
            return ResponseEntity.badRequest().body(userDTO);
        }
    }

    public boolean validateUser(UserDTO userDTO){
        if (!isValidEmail(userDTO.getEmail())) {
            throw new InvalidEmailException("El formato del email es inválido.");
        }
        if (!isValidPhoneNumber(userDTO.getPhoneNumber())) {
            throw new InvalidPhoneNumberException("El formato del número de teléfono es inválido.");
        }
        return true;
    }

    public boolean isValidEmail(String email){
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        String mobilePhoneRegex = "^\\+?54\\s?9\\s?(11|[2368]\\d)\\s?\\d{4}\\s?-?\\s?\\d{4}$"; // Números móviles
        String landlinePhoneRegex = "^\\+?54\\s?(11|[2368]\\d)\\s?\\d{4}\\s?-?\\s?\\d{4}$"; // Números fijos
        return phoneNumber.matches(mobilePhoneRegex) || phoneNumber.matches(landlinePhoneRegex);
    }

    public UserDTO formatedUserDTO(UserDTO userDTO){
        return userFormatter.formattedUserDTO(userDTO);
    }

}

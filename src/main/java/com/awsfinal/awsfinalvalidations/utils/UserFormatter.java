package com.awsfinal.awsfinalvalidations.utils;

import com.awsfinal.awsfinalvalidations.DTOs.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.Normalizer;
import java.util.regex.Pattern;

@Component
public class UserFormatter {

    @Autowired
    DateUtils dateUtils;
    public UserDTO formattedUserDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        String firstName = formatName(userDTO.getFirstName());
        String lastName = formatName(userDTO.getLastName());
        String email = formatEmail(userDTO.getEmail());
        String phoneNumber = formatPhoneNumber(userDTO.getPhoneNumber());
        String birthday = dateUtils.parseAndFormatBirthdate(userDTO.getBirthdate(),"America/Argentina/Buenos_Aires");
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPhoneNumber(phoneNumber);
        userDTO.setBirthdate(birthday);

        return userDTO;
    }

    private String formatName(String name) {
        if (name == null) {
            return null;
        }
        return capitalizeFully(normalizeString(name.trim()));
    }

    private String formatEmail(String email) {
        return (email == null) ? null : email.toLowerCase().trim();
    }

    private String formatPhoneNumber(String phoneNumber) {
        return (phoneNumber == null) ? null : phoneNumber.trim();
    }

    private String normalizeString(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private String capitalizeFully(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        int firstSpaceIndex = input.indexOf(" ");
        if (firstSpaceIndex == -1) {
            return input.toUpperCase();
        } else {
            String firstWord = input.substring(0, firstSpaceIndex).toUpperCase();
            String remainingText = input.substring(firstSpaceIndex).toLowerCase();
            return firstWord + remainingText;
        }
    }

}

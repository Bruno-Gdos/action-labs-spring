package br.com.actionlabs.carboncalc.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class Validator {

    private static final List<String> VALID_UFS = Arrays.asList(
        "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PE", "PI", "PR", "RJ", "RN",
        "RO", "RR", "RS", "SC", "SE", "SP", "TO"
    );

    private static final String PHONE_NUMBER_REGEX = "^\\+?[0-9\\s()-]{7,15}$"; 
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";



    public static void validateUF(String uf) throws IllegalArgumentException  {
        if (!VALID_UFS.contains(uf.toUpperCase())) {
            throw new IllegalArgumentException("Invalid UF: " + uf);
        }
    }

    public static void validatePhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (!Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }

    public static void validateEmail(String email) throws IllegalArgumentException {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    public static void validateRecyclePercentage(double recyclePercentage) throws IllegalArgumentException {
        if (recyclePercentage < 0.0 || recyclePercentage > 1.0) {
            throw new IllegalArgumentException("Invalid recyclePercentage: must be between 0.0 and 1.0.");
        }
    }
}

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

    private static final String PHONE_NUMBER_REGEX = "^\\+?[0-9\\s()-]{7,15}$"; // Máscara para telefone

    public static void validateUF(String uf) {
        if (!VALID_UFS.contains(uf.toUpperCase())) {
            throw new IllegalArgumentException("Invalid UF: " + uf);
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (!Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }
}

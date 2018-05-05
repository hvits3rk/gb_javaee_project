package com.romantupikov.simpleapp.security.control;

public interface HashGenerator {

    String getHashedText(final String text);

    boolean isHashedTextMatch(final String text, final String hashedText);
}

package com.showcar.showcar.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Custom PasswordEncoder implementation using Argon2 algorithm
 * Argon2 is the winner of the Password Hashing Competition (PHC)
 * It's more secure and resistant to GPU/ASIC attacks than BCrypt
 */
public class Argon2PasswordEncoder implements PasswordEncoder {

    private static final Argon2 ARGON2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    // Argon2 parameters - adjust based on your performance requirements
    private static final int ITERATIONS = 2;          // Number of iterations
    private static final int MEMORY = 65536;          // Memory usage in KB (64 MB)
    private static final int PARALLELISM = 1;        // Number of threads

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Raw password cannot be null");
        }
        
        char[] password = rawPassword.toString().toCharArray();
        try {
            // Hash password with Argon2id
            return ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, password);
        } finally {
            // Clear password from memory for security
            ARGON2.wipeArray(password);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            return false;
        }
        if (encodedPassword == null || encodedPassword.isEmpty()) {
            return false;
        }

        char[] password = rawPassword.toString().toCharArray();
        try {
            // Verify password against hash
            return ARGON2.verify(encodedPassword, password);
        } catch (Exception e) {
            // Log error in production
            return false;
        } finally {
            // Clear password from memory for security
            ARGON2.wipeArray(password);
        }
    }

    /**
     * Upgrade encoding if needed (for migrating from other algorithms)
     * This method checks if the encoded password needs to be re-encoded
     */
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // Check if password was encoded with older Argon2 parameters
        // You can implement logic to detect if re-encoding is needed
        // For now, return false (no upgrade needed)
        return false;
    }
}


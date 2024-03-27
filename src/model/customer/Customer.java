package model.customer;

import java.util.regex.Pattern;

/**
 * Customer
 * <p>
 * Represents a customer with a first name, last name, and email.
 * Provides methods for creating and validating customer objects.
 *
 * @author ahmad deni atmaja saputra
 */
public class Customer {

    // Regular expression pattern for email validation
    private static final String EMAIL_REGEX_PATTERN = "^(.+)@(.+)\\.(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX_PATTERN);

    private final String firstName;
    private final String lastName;
    private final String email;

    /**
     * Constructs a customer object with the specified first name, last name, and email.
     *
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @param email the email address of the customer
     * @throws IllegalArgumentException if the email format is incorrect
     */
    public Customer(String firstName, String lastName, String email) {
        validateEmail(email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Validates the email format using a regular expression pattern.
     *
     * @param email the email address to validate
     * @throws IllegalArgumentException if the email format is incorrect
     */
    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Please provide a correct email format!");
        }
    }

    /**
     * Checks if the given email address is in a valid format.
     *
     * @param email the email address to check
     * @return true if the email address is valid, false otherwise
     */
    @SuppressWarnings("unused")
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Gets the email address of the customer.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns a string representation of the customer.
     *
     * @return a string containing the customer's first name, last name, and email address
     */
    @Override
    public String toString() {
        return "Customer: {" +
                "First Name: '" + firstName + '\'' +
                ", Last Name: '" + lastName + '\'' +
                ", Email: '" + email + '\'' +
                '}';
    }
}

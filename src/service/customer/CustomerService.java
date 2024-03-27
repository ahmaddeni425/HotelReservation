package service.customer;

import model.customer.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomerService
 * <p>
 * Manages customer-related operations such as adding customers, retrieving customers by email,
 * and retrieving all customers.
 * Implements a singleton design pattern.
 * Uses a HashMap to store customers, where the key is the customer's email.
 *
 * @author ahmad deni atmaja saputra
 */
public class CustomerService {

    private static final CustomerService SINGLETON = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    /**
     * Returns the singleton instance of CustomerService.
     *
     * @return the singleton instance of CustomerService
     */
    public static CustomerService getSingleton() {
        return SINGLETON;
    }

    /**
     * Adds a new customer with the given email, first name, and last name.
     *
     * @param email the email of the customer
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     */
    public void addCustomer(final String email, final String firstName, final String lastName) {
        customers.put(email, new Customer(firstName, lastName, email));
    }

    /**
     * Retrieves the customer with the specified email.
     *
     * @param customerEmail the email of the customer to retrieve
     * @return the customer with the specified email, or null if not found
     */
    public Customer getCustomer(final String customerEmail) {
        return customers.get(customerEmail);
    }

    /**
     * Retrieves all customers stored in the service.
     *
     * @return a collection containing all customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}

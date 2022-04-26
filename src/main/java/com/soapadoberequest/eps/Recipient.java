package com.soapadoberequest.eps;

/**
 * This class allows to create a simple Recipient
 */
public class Recipient {
    private String firstName;
    private String lastName;
    private String email;

    /**
     * The constructor of Recipient
     *
     * @param firstName Firstname of the recipient
     * @param lastName Lastname of the recipient
     * @param email Email of the recipient
     */
    public Recipient(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Get recipient's firstname
     *
     * @return firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get recipient's lastname
     *
     * @return lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get recipient's email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

}
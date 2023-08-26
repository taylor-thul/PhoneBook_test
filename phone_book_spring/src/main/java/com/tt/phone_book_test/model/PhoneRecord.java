/**
 * 
 */
package com.tt.phone_book_test.model;

import java.util.regex.Pattern;
import jakarta.persistence.*;

@Entity
@Table(name="records")
public class PhoneRecord
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "firstname", nullable = false) // required
    private String firstname;

    @Column(name = "middleinitial") // TBD: Should do check for length <= 1 ?
    private String middleInitial;

    @Column(name = "lastName") // not required?
    private String lastName;

    @Column(name = "phonenumber") // optional(?) + validated
    private String phoneNumber;

    @Column(name = "email") // optional + validated
    private String email;

    @Column(name = "address")
    private String address;

    //----------------------------------------------------------------------------------------------------------
    private PhoneRecord() {}

    private PhoneRecord(String firstName, String middleInitial, String lastName, String phoneNumber, String email, String address)
    {
        this.firstname      = notNull(firstname);
        this.middleInitial  = notNull(middleInitial);
        this.lastName       = notNull(lastName);
        this.phoneNumber    = notNull(phoneNumber);
        this.email          = notNull(email);
        this.address        = notNull(address);
    }

    private PhoneRecord(PhoneRecord o)
    {
        this.firstname      = notNull(o.firstname);
        this.middleInitial  = notNull(o.middleInitial);
        this.lastName       = notNull(o.lastName);
        this.phoneNumber    = notNull(o.phoneNumber);
        this.email          = notNull(o.email);
        this.address        = notNull(o.address);
    }

    private static String notNull(String s) { return (s == null) ? "" : s; }

    //----------------------------------------------------------------------------------------------------------
    /**
     * Returns a new PhoneRecord with the validated paramters.
     * 
     * If invalid, returns null
     * 
     * @param firstName
     * @param middleInitial
     * @param lastName
     * @param phoneNumber
     * @param email
     * @param address
     * @return
     */
    public static PhoneRecord create(String firstName, String middleInitial, String lastName, String phoneNumber, String email, String address)
    {
        if( validate(firstName, middleInitial, lastName, phoneNumber, email, address) )
        {
            return new PhoneRecord(firstName, middleInitial, lastName, phoneNumber, email, address);
        }
        else
        {
            return null;
        }
    }


    //----------------------------------------------------------------------------------------------------------
    /**
     * Returns a copy of the PhoneRecord (generates a new id), validatint the original PhoneRecord
     * 
     * If the original record is invalid, returns null
     * 
     * @param orig
     * @return
     */
    public static PhoneRecord create(PhoneRecord orig)
    {
        if( validate(orig) ) return new PhoneRecord(orig);
        else                 return null; // TBD: Return 'bad object' instead of null?
    }


    //----------------------------------------------------------------------------------------------------------
    /**
     * TBD: Do we care about duplicates existing? They'll have unique ids regardless
     * 
     * First name should not be null/empty.
     * Email is optional + validated
     * PhoneNumbe is optional + validated
     * 
     * Everything else is optional and not validated (including last name?)
     * 
     * TODO: Return enum/obj indicating valid state or containing specific issue(s)
     */
    public static boolean validate(PhoneRecord record)
    {
        return validate(record.firstname, record.middleInitial, record.lastName, record.phoneNumber, record.email, record.address);
    }

    public static boolean validate(String firstName, String middleInitial, String lastName, String phoneNumber, String email, String address)
    {
        if( (firstName == null) || firstName.isBlank() )  return false; // have validate function?

        if( ! validateEmail(email) ) return false;

        if( ! validatePhoneNumber(phoneNumber) ) return false;

        return true;
    }

    /**
     * TBD: If other items are using email validation, should move this to different class
     * 
     * Only performing regex check if not null/empty. Not validating email actually exists
     * 
     * Using regex 6 (RFC 5322) from: https://www.baeldung.com/java-email-validation-regex
     */
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static boolean validateEmail(String email)
    {
        if((email == null) || email.isBlank()) return true; // empty/null valid

        return Pattern.matches(EMAIL_REGEX, email);
    }


    /**
     * TBD: If other items are using phone# validation, should move this to different class
     * Only performing regex check if not null/empty.
     * 
     * Regex matching exact pattern: XXX.XXX.XXXX
     * 
     */
    private static final String PHONE_NUMBER_REGEX = "^(\\d{3}[.]){2}\\d{4}$";
    public static boolean validatePhoneNumber(String phoneNumber)
    {
        if((phoneNumber == null) || phoneNumber.isEmpty()) return true;

        return Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber);
    }


    //----------------------------------------------------------------------------------------------------------
    public long getId()                 { return id; }

    public String getFirstname()        { return firstname; }

    public String getMiddleInitial()    { return middleInitial; }

    public String getLastName()         { return lastName; }

    public String getPhoneNumber()      { return phoneNumber; }

    public String getEmail()            { return email; }

    public String getAddress()          { return address; }


    //----------------------------------------------------------------------------------------------------------
    public void setFirstname(String firstName)          { this.firstname    = firstName; }

    public void setMiddleInitial(String middleInitial)  { this.middleInitial = middleInitial; }

    public void setLastName(String lastName)            { this.lastName      = lastName; }

    public void setPhoneNumber(String phoneNumber)      { this.phoneNumber   = phoneNumber;}

    public void setEmail(String email)                  { this.email         = email; }

    public void setAddress(String address)              { this.address       = address; }

    //----------------------------------------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return "PhoneRecord[firstname=" + firstname + ", middleInitial= " + middleInitial +", lastName=" + lastName + "]";
    }
}

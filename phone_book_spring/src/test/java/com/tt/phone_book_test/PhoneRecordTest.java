/**
 * Validate PhoneRecord validation against:
 *  - phone number (can be blank/null)
 *  - email (can be blank/null)
 * 
 * TBD: Currently not validating any of the other fields (valid ascii or otherwise).
 *      Should include validation of other fields if/when constricting other fields
 */
package com.tt.phone_book_test;

import org.junit.jupiter.api.Test;

import com.tt.phone_book_test.model.PhoneRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneRecordTest
{
    
    //--------------------------------------------------------------------------------------------------
    @Test
    public void testValidPhoneNumbers()
    {
        String[] validPhoneNumbers = {"123.456.7890", "111.111.1111", "", "     ", null};

        for(String number : validPhoneNumbers)
        {
            boolean isValid = PhoneRecord.validatePhoneNumber( number );
            assertEquals(true, isValid, "String \"" + number + "\"");
        }
    }

    //--------------------------------------------------------------------------------------------------
    @Test
    public void testInvalidPhoneNumbers()
    {
        String[] invalidPhoneNumbers = {"123-456-7890", "1111111111", "11", "XXX.XXX.XXXX", "abc", 
                                        "123.456.789", "123.45.7890", "12.456.7890", "123.456.78900", 
                                        "123.4567.7890", "1234.456.7890", "..."};

        for(String number : invalidPhoneNumbers)
        {
            boolean isValid = PhoneRecord.validatePhoneNumber( number );
            assertEquals(false, isValid, "String \"" + number + "\"");
        }
    }

    //--------------------------------------------------------------------------------------------------
    @Test
    public void testValidEmails()
    {
        String[] validEmails = {"taylor.r.thul@gmail.com", "test@hotmail", "test@test-mail", "", "     ", null};

        for(String email : validEmails)
        {
            boolean isValid = PhoneRecord.validateEmail( email );
            assertEquals(true, isValid, "String \"" + email + "\"");
        }
    }

    //--------------------------------------------------------------------------------------------------
    @Test
    public void testInvalidEmails()
    {
        String[] invalidEmails = {"not-an-email", "@", "test@email@test@.com", "mail@", "@mail"};

        for(String email : invalidEmails)
        {
            System.out.println("Testing " + email);
            boolean isValid = PhoneRecord.validateEmail( email );
            assertEquals(false, isValid, "String \"" + email + "\"");
        }
    }
}

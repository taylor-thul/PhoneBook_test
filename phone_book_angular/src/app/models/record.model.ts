/*
TBD: Shouldm maybe ask the server for the regex/rules? That way if back-end changes regex, front-end will inherit
*/

export class PhoneRecord {
    id?: number | null;
    firstName?: string;
    middleInitial?: string;
    lastName?: string;
    phoneNumber?: string;
    email?: string;
    address?: string;


    // First name must be defined, no hard restrictions
    static validateFirstName(firstName?: string): boolean
    {
        return ((typeof firstName !== undefined) &&
                (firstName != null) &&
                (firstName.trim().length > 0));
    }


    // must be empty or of format XXX.XXX.XXX
    static validatePhoneNumber(phoneNumber?: string): boolean
    {
        if( (typeof phoneNumber === undefined) || 
            (phoneNumber == null) || 
            (phoneNumber.trim().length == 0)) return true;

        const regex: RegExp = new RegExp("^(\\d{3}[.]){2}\\d{4}$");

        return regex.test(phoneNumber);
    }

    // must be empty or have a valid email
    static validateEmail(email?: string): boolean
    {
        if( (typeof email === undefined) || 
            (email == null) || 
            (email.trim().length == 0)) return true;

        const regex: RegExp = new RegExp("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

        return regex.test(email);
    }
}



/**
 * TBD: Add in requests to get regex validation strings?
 * 
 */
package com.tt.phone_book_test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tt.phone_book_test.model.PhoneRecord;
import com.tt.phone_book_test.repository.PhoneRecordRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PhoneBookController 
{
    @Autowired
    private PhoneRecordRepository phoneRecordRepository;

    
    //----------------------------------------------------------------------------------------------
    @GetMapping("/records")
    public ResponseEntity<List<PhoneRecord>> listAll()
    {
        try
        {
            return new ResponseEntity<>(phoneRecordRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //----------------------------------------------------------------------------------------------
    @GetMapping("/records/{id}")
    public ResponseEntity<PhoneRecord> getById(@PathVariable("id") long id)
    {
        Optional<PhoneRecord> tutorialData = phoneRecordRepository.findById(id);

		if( tutorialData.isPresent() ) return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //----------------------------------------------------------------------------------------------
    @PostMapping("records/add")
    public ResponseEntity<PhoneRecord> createPhoneRecord(@RequestBody PhoneRecord record)
    {
        if( PhoneRecord.validate(record) == false)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE); 
        }

        PhoneRecord ret = phoneRecordRepository.save( record );
        if(ret == null)
        {
            // Generate some additional message here to report back to the user?
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else
        {
            return new ResponseEntity<>(ret, HttpStatus.CREATED);
        }
    }

    //----------------------------------------------------------------------------------------------
    @PostMapping("records/update")
    public ResponseEntity<PhoneRecord> updatePhoneRecord(@RequestBody PhoneRecord record)
    {
        if( PhoneRecord.validate(record) == false)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE); 
        }

        try
        {
            // Delete old record
            phoneRecordRepository.deleteById( record.getId() ); 

            // Now add new
            PhoneRecord ret = phoneRecordRepository.save( record );
            if(ret == null)
            {
                // Generate some additional message here to report back to the user?
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else
            {
                return new ResponseEntity<>(ret, HttpStatus.CREATED);
            }
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //----------------------------------------------------------------------------------------------
    @PostMapping("records/delete")
    public ResponseEntity<PhoneRecord> deletePhoneRecord(@RequestBody String idString)
    {
        try
        {
            long id = Long.parseLong(idString);

            Optional<PhoneRecord> exists = phoneRecordRepository.findById(id);
            if( exists.isPresent() ) 
            {
                PhoneRecord rec = exists.get();
                phoneRecordRepository.delete(rec);
                return new ResponseEntity<>(rec, HttpStatus.ACCEPTED);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.ACCEPTED); // Accepted is fine?
            }
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

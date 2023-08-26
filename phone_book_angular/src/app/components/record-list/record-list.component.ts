import { Component, OnInit } from '@angular/core';
import { PhoneRecord } from 'src/app/models/record.model';
import { PhonebookService } from 'src/app/services/phonebook.service';

@Component({
  selector: 'app-record-list',
  templateUrl: './record-list.component.html',
  styleUrls: ['./record-list.component.css']
})

/**
 * Lists all PhoneRecords, with ability to delete/edit/add
 * 
 * TODO: Should maybe add in a scroll-pane and/or split up entries across multiple table entires (maybe 100-ish per page?)
 *      May want to update back-end to be able to specifically request only certain data sets
 *      if data were expected to get large. (ex; requesting records 3601 -> 3700)?
 */
export class RecordListComponent implements OnInit
{
    ngOnInit(): void { this.populateRecords(); }

    // all records
    records: PhoneRecord[] = [];

    // new record (either adding new or updating current)
    newRecord: PhoneRecord = {
      id: null,
      firstName: '',
      middleInitial: '',
      lastName: '',
      phoneNumber: '',
      email: '',
      address: '',
    };

    // adding/updating strings
    dialogDisplayText: string = "";
    dialogDisplayButtonText: string = "";
  
    // validation error string
    addOrEditMessage: string = "";

    //----------------------------------------------------------------------------
    constructor(private phoneBookService: PhonebookService) {}

    //----------------------------------------------------------------------------
    populateRecords(): void
    {
      // this.clearRecords();
      this.records = [];

      this.phoneBookService.getAll().subscribe(
        {
          next: (rec) =>
          {
            this.records = rec;
          },

          error: (e) => 
          {
            // TODO: Report back some status?
            console.error(e);
          }
        })
    }

    //----------------------------------------------------------------------------
    deleteRecord(id?: any): void
    {
      this.phoneBookService.delete(id).subscribe(
        {
          next: (rec) =>
          {
            // either got the deleted record or null (it wasn't in the db?)
            this.populateRecords();
          },

          error: (e) => 
          {
            // TODO: Report back some status?
            console.error(e);
          }
        })
    }


    //----------------------------------------------------------------------------
    /**
     * User add/delete button. Validate changes and propogate request to server
     * 
     * @returns 
     */
    submitPhoneRecord(): void
    {
      const data: PhoneRecord = {
        id:             this.newRecord.id, // will be null if adding, or set if editing
        firstName:      this.newRecord.firstName,
        middleInitial:  this.newRecord.middleInitial,
        lastName:       this.newRecord.lastName,
        phoneNumber:    this.newRecord.phoneNumber,
        email:          this.newRecord.email,
        address:        this.newRecord.address,
      };
  
      if(this.validateNewRecord(data) == false) return;

      if(data.id == null)   this.addRecord(data);
      else                  this.updateRecord(data);
    }

    //----------------------------------------------------------------------------
    private addRecord(newRecord: PhoneRecord): void
    {
      this.phoneBookService.create(newRecord).subscribe(
        {
          next: (rec) => 
          {
            console.log(rec);
            this.clearDialogForm();
            this.populateRecords();
            this.addOrEditMessage = "Succesfully added new record.";
          },
          error: (e) => 
          { 
            //TODO: Get reason to display to user
            console.error(e)
            this.addOrEditMessage = "Unable to process data. Double-check that your email and phone numbers are correct."
          }
        });
    }

    //----------------------------------------------------------------------------
    private updateRecord(updatedRecord: PhoneRecord): void
    {
      this.phoneBookService.update(updatedRecord).subscribe(
        {
          next: (rec) => 
          {
            console.log(rec);
            this.clearDialogForm();
            this.populateRecords();
            this.addOrEditMessage = "Succesfully updated record.";
          },
          error: (e) => 
          { 
            //TODO: Get reason to display to user
            console.error(e)
            this.addOrEditMessage = "Unable to process data. Double-check that your email and phone numbers are correct."
          }
        });
    }

  
    //----------------------------------------------------------------------------
    private validateNewRecord(data: PhoneRecord): boolean
    {
      this.addOrEditMessage = "";
  
      let valid: boolean = true;
      if( PhoneRecord.validateFirstName( data.firstName ) == false )
      {
        valid = false;
        this.addOrEditMessage += "Must enter a first name.\n";
      }
  
      if( PhoneRecord.validatePhoneNumber( data.phoneNumber ) == false )
      {
        valid = false;
        this.addOrEditMessage += "Optional phone number must be of the following format: XXX.XXX.XXXX (with periods).\n";
      }
  
      if( PhoneRecord.validateEmail( data.email ) == false)
      {
        valid = false;
        this.addOrEditMessage += "Optional email must be of a valid format.\n";
      }
  
      return valid;
    }
  
    //----------------------------------------------------------------------------
    private clearDialogForm(): void
    {
      this.newRecord = {
        id: null,
        firstName: '',
        middleInitial: '',
        lastName: '',
        phoneNumber: '',
        email: '',
        address: '',
      };
  
      this.addOrEditMessage = "";
    }

    //----------------------------------------------------------------------------
    /**
     * User hit "add" button. Update dialog text and remove edit variables
     */
    configureForAdd(): void
    {
      this.dialogDisplayText = "Create new Phone Record";
      this.dialogDisplayButtonText = "Add";

      this.clearDialogForm();
    }

    //----------------------------------------------------------------------------
    /**
     * User hit "edit" button. Update dialog text and set newRecord values to 
     * table entry selected
     */
    configureForEdit(oldRecord: PhoneRecord): void
    {
      this.dialogDisplayText = "Edit Phone Record";
      this.dialogDisplayButtonText = "Update";

      this.clearDialogForm();

      this.newRecord = {
        id:             oldRecord.id,
        firstName:      oldRecord.firstName,
        middleInitial:  oldRecord.middleInitial,
        lastName:       oldRecord.lastName,
        phoneNumber:    oldRecord.phoneNumber,
        email:          oldRecord.email,
        address:        oldRecord.address,
      };
    }
}

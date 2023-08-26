import { Component, OnInit } from '@angular/core';
import { PhoneRecord } from 'src/app/models/record.model';
import { PhonebookService } from 'src/app/services/phonebook.service';

@Component({
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.css']
})

export class AddRecordComponent implements OnInit
{
  record: PhoneRecord = {
    firstname: '',
    middleinitial: '',
    lastname: '',
    phonenumber: '',
    email: '',
    address: '',
  };

  added = false;

  constructor(private phoneBookService: PhonebookService) {}

  ngOnInit(): void {}

  addPhoneRecord(): void
  {
    // validate here?

    const data = {
      firstname:      this.record.firstname,
      middleinitial:  this.record.middleinitial,
      lastname:       this.record.lastname,
      phonenumber:    this.record.phonenumber,
      email:          this.record.email,
      address:        this.record.address,
    };

    this.phoneBookService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.added = true;
        },
        error: (e) => console.error(e)
      });
  }

  newPhoneRecord(): void
  {
    this.record = {
      firstname: '',
      middleinitial: '',
      lastname: '',
      phonenumber: '',
      email: '',
      address: '',
    };
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-review-component',
  templateUrl: './search-review-component.component.html',
  styleUrls: ['./search-review-component.component.css']
})

export class SearchReviewComponentComponent implements OnInit {
  
  form!:FormGroup
  inputValue: string = '';
  isButtonEnabled: boolean = false;
  query!:string


  constructor(private formBuilder:FormBuilder, private httpClient: HttpClient, private router : Router) {}
  
  invalid(): boolean {
    return this.form.invalid || this.form.get('movieName')?.value.trim().length <= 1
   
  }

  onInputChange(value: string): void {
    this.inputValue = value.trim();
    this.isButtonEnabled = this.inputValue.length >= 2;
  }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  submit(){
    this.query = this.form.controls['movieName'].value.trim()
    console.log(">>Query: ", this.query)
    this.router.navigate(["/search"], {queryParams: {query: this.query}})
    this.form.reset()
  }

  private createForm(): FormGroup {
    return this.formBuilder.group({
      movieName: this.formBuilder.control<string>('', Validators.required)
    })
  }

}

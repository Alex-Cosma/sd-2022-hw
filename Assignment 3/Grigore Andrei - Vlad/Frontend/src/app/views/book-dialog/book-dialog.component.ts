import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Book, LibraryComponent} from "../library/library.component";
import{ActivatedRoute} from "@angular/router";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Component({
  selector: 'app-book-dialog',
  templateUrl: './book-dialog.component.html',

  styleUrls: ['./book-dialog.component.css']
})

export class BookDialogComponent implements OnInit {
  public selectBook: Book =  {id:0,title:'',author:'',genre:'',quantity:0,price:0};
  public book: Book = {id:0,title:'',author:'',genre:'',quantity:0,price:0};
  private createUrl = 'http://localhost:8080/api/books/create'
  private editUrl = `http://localhost:8080/api/books/edit`
  constructor(private http:HttpClient, private router:Router,private activatedRoute:ActivatedRoute) {
    this.selectBook = JSON.parse(activatedRoute.snapshot.params["selectBook"])
  }
  close(){
    this.router.navigate(['books'])
  }

  create(){
    this.http.post(this.createUrl,JSON.stringify(this.selectBook),httpOptions).subscribe((response)=>{
      console.log("Book created")
      this.router.navigate(['books'])
    })
  }

  edit(){
    this.http.post(`${this.editUrl}/${this.selectBook.id}`,JSON.stringify(this.selectBook),httpOptions).subscribe((response)=>{
      console.log("Book edited")
      this.router.navigate(['books'])
    })
  }
  ngOnInit(): void {
  }

}

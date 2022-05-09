import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpClientModule, HttpParams} from "@angular/common/http";
import { FormControl } from "@angular/forms";

export class BookElement {
  constructor(
    public title: string,
    public id: number,
    public author: string,
    public genre: string,
    public quantity: number,
    public price: number
  ) {
  }
}

@Component({
  selector: 'app-book-regular',
  templateUrl: './book-regular.component.html',
  styleUrls: ['./book-regular.component.css']
})
export class BookRegularComponent implements OnInit {

  searchText: string = "";
  searchTitle: string = "";
  searchAuthor: string = "";
  bookElements: BookElement[] = [];
  apiUrl = 'http://localhost:8088/api/books';
  filterUrl = 'http://localhost:8088/api/books';

  constructor(
    private httpClient: HttpClient
  ) { }

  getBooks(){
    this.httpClient.get<any>(this.apiUrl).subscribe(
      (response) => {
        console.log(response);
        this.bookElements = response;
      }
    )
  }

  sellBook(book:any){
    let params = new HttpParams().set("id", book.id);
    this.httpClient.patch(`http://localhost:8088/api/books/${book.id}`,{params}).subscribe(
      (response) =>{
        this.getBooks();
      }
    );
  }

  ngOnInit() {
    this.getBooks();
  }

  applyFilterTitle(){
    let params = new HttpParams().set("title", this.searchTitle);
    console.log(this.searchTitle);
    this.httpClient.get<any>(`http://localhost:8088/api/books/filtered/${this.searchTitle}`,{params}).
      subscribe(
      (response) => {
        this.bookElements = response;
        }
    )
  }

  applyFilterAuthor(){
    let params = new HttpParams().set("author", this.searchAuthor);
    console.log(this.searchAuthor);
    this.httpClient.get<any>(`http://localhost:8088/api/books/filtered/author/${this.searchAuthor}`,{params}).
    subscribe(
      (response) => {
        this.bookElements = response;
      }
    )
  }

  applyFilterGenre() {
    let params = new HttpParams().set("genre", this.searchText);
    console.log(this.searchText);
    this.httpClient.get<any>(`http://localhost:8088/api/books/${this.searchText}`,{params}).
    subscribe(
      (response) => {
        this.bookElements = response;
      }
    )
  }

}

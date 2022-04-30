import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpClientModule, HttpHeaders, HttpParams} from "@angular/common/http";
import {ActivatedRoute,provideRoutes, Router, ROUTES} from "@angular/router";

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

export class ApiBook{
  constructor(
    public title: string,
    public subtitle: string,
    public isbn13: number,
    public price: string,
    public image: string,
    public url: string
  ) {
  }
}

export class ApiResponse{
  constructor(
    public total: number,
    public books: ApiBook[]
  ) {
  }
}


@Component({
  selector: 'app-book-page',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.css']
})
export class BookPageComponent implements OnInit{
    bookElements: BookElement[] = [];
    public bookToEdit : BookElement = {id :0, title:"",author:"",genre:"",quantity:0,price:0};
    public bookFromApi : BookElement = {id :0, title:"",author:"",genre:"",quantity:0,price:0};
    public apiResponse: ApiResponse = {total: 0, books: []}
    public apiBooks: ApiBook[] = [];
    public genres: string[] = ["ACTION","FANTASY","SCIFI","ADVENTURE","COMEDY"];
    apiUrl = 'http://localhost:8088/api/books';
    deleteUrl = 'http://localhost:8088/api/books/';
    reportUrl = 'http://localhost:8088/api/books/report';
    bookApiUrl = 'https://api.itbook.store/1.0/new'
    status = '';
    private createUrl = 'http://localhost:8088/api/books/create'

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  fill(book:any){
    this.bookToEdit = book;
    console.log(this.bookToEdit.author);
  }

  getBooks(){
    this.httpClient.get<any>(this.apiUrl).subscribe(
      (response) => {
        console.log(response);
        this.bookElements = response;
      }
    )
  }

  goToUsers(){
    this.router.navigate(['users'])
  }

  ngOnInit() {
      this.httpClient.get<ApiResponse>(this.bookApiUrl).subscribe((respnse) => {
        this.apiResponse = respnse;
        this.apiBooks = this.apiResponse.books;
        this.apiBooks.forEach((value) =>{
           this.bookFromApi.title = value.title;
           this.bookFromApi.author = "JJ";
           this.bookFromApi.quantity = 10;
           this.bookFromApi.price = +value.price.slice(1,3);
           this.bookFromApi.genre = this.genres[~~(Math.random()*5)];
          const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'})
          }
          this.httpClient.post(this.createUrl,JSON.stringify(this.bookFromApi),httpOptions).subscribe((data) => {
            console.log("book created")
          })
        })
        this.getBooks();
     })
  }

  create(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    this.httpClient.post(this.createUrl,JSON.stringify(this.bookToEdit),httpOptions).subscribe((data) => {
      console.log("book created")
    })
    this.getBooks();
  }

  deleteBook(book:any){
    let params = new HttpParams().set("id", book.id);
    this.httpClient.delete(`http://localhost:8088/api/books/${book.id}`,{params}).subscribe(
      (response) =>{
        this.getBooks();
      }
    );
  }

  edit(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    let params = new HttpParams().set("id", this.bookToEdit.id);
    this.httpClient.put(`http://localhost:8088/api/books/${this.bookToEdit.id}`,JSON.stringify(this.bookToEdit),httpOptions).
    subscribe((response)=>{
      console.log("book edited");
    })
  }

  generateReports(type:string){
    let params = new HttpParams().set("type",type);
    this.httpClient.get(`${this.reportUrl}/${type}`,{params}).subscribe();
  }

  applyFilter() {
  }
}

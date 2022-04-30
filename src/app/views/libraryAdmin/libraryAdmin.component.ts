import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpEvent, HttpHandler, HttpRequest, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {ActivatedRoute, provideRoutes, Router, ROUTES} from "@angular/router";
import {User} from "../user/user.component";
import {BookDialogComponent} from "../book-dialog/book-dialog.component";
import {Api, BookApi, BookIsbn} from "../library/library.component";

const httpOptions : any    = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Access-Control-Allow-Headers': 'Content-Type',
    'Access-Control-Allow-Methods': 'GET',
    'Access-Control-Allow-Origin': '*'
  })
};


export interface Book{
  id:number;
  title:string;
  author:string;
  genre:string;
  quantity:number;
  price:number;
}

interface HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
}


const books: Book[] = [{id:1,title:"Damn Daniel",author:"Daniel",genre:"Meme Culture",quantity:10,price:100},
  {id:2,title:"Damn Daniel2",author:"Daniel",genre:"Meme Culture",quantity:10,price:100}
  ];
@Component({
  selector: 'app-library',
  templateUrl: './libraryAdmin.component.html',
  styleUrls: ['./libraryAdmin.component.css']
})


export class LibraryAdminComponent implements OnInit {
  constructor(private http: HttpClient, private router:Router, private route:ActivatedRoute) {
  }
  private apiServerUrl = environment.apiBaseUrl;
  author:string = '';
  dataSource = books;
  apiBooks: BookApi[]= [];
  displayedColumns :string[] = ["id","title","author","genre","quantity","price","actions"];
  public selectBook: Book = {id:0,title:'',author:'',genre:'',quantity:0,price:0}
  generate(){
    this.http.get<any>(`${this.apiServerUrl}/books`).subscribe((response)=> {
        console.log(response)
        this.dataSource = response
      }
    )
  }
  getAuthor(isbn:string){
    this.http.get<BookIsbn>(`https://api.itbook.store/1.0/books/${isbn}`).subscribe((response)=> {
        this.author = response.authors;
        console.log(this.author)
      }
    )
  }

  generateApi(){
    this.http.get<Api>('https://api.itbook.store/1.0/new').subscribe((response)=>{
        this.apiBooks=response.books;
        for(let i=0;i<this.apiBooks.length;i++){
          let book:Book = {id:0,title:'',author:'',genre:'',quantity:0,price:0}
          book.title=this.apiBooks[i].title;
          book.author = "UNKOWN"
          book.genre = "UNKOWN"
          book.quantity=10;
          book.price = Number(this.apiBooks[i].price.replace(/[^\d]/g, ''))
         this.save(book)
          console.log(book.price)
        }
    })
    this.generate()
  }
  save(book:Book){
    this.http.post('http://localhost:8080/api/books/create',JSON.stringify(book),httpOptions).subscribe()
  }

  create(){
    this.router.navigate(['bookEdit',JSON.stringify(this.selectBook)])
  }

  edit(){
    this.router.navigate(['bookEdit',JSON.stringify(this.selectBook)])
    console.log(this.selectBook.id)
  }

  deleteBook(book:Book){
    let params = new HttpParams().set("id", book.id);
    this.http.delete(`${this.apiServerUrl}/books/delete/${book.id}`,{params}).subscribe((response)=>{
      this.generate();
    })
  }

  filter(title:string){
    let params = new HttpParams().set("title", title);
    if(title==''){
      this.generate()
    }
    this.http.get<any>(`${this.apiServerUrl}/books/filter/${title}`,{params}).subscribe((response)=> {
        this.dataSource = response
        console.log(response)
      }
    )
  }

  filterAuthor(author:string){
    let params = new HttpParams().set("author", author);
    if(author==''){
      this.generate()
    }
    this.http.get<any>(`${this.apiServerUrl}/books/filterAuthor/${author}`,{params}).subscribe((response)=> {
        this.dataSource = response
        console.log(response)
      }
    )
  }

  filterGenre(genre:string){
    let params = new HttpParams().set("genre", genre);
    if(genre==''){
      this.generate()
    }
    this.http.get<any>(`${this.apiServerUrl}/books/filterGenre/${genre}`,{params}).subscribe((response)=> {
        this.dataSource = response
        console.log(response)
      }
    )
  }

  reports(type: string | number | boolean): void{
    let apiServerUrl;
    apiServerUrl = environment.apiBaseUrl;
    let params = new HttpParams().set('type', type);
    this.http.get(`${apiServerUrl}/books/report/${type}`,{params:params}).subscribe((response)=>
      console.log("should have something here")
    );
    alert("clicked")
  }
  ngOnInit(): void {
    this.generate();
  }
}

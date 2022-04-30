import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpEvent, HttpHandler, HttpRequest, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {ActivatedRoute, provideRoutes, Router, ROUTES} from "@angular/router";
import {User} from "../user/user.component";
import {BookDialogComponent} from "../book-dialog/book-dialog.component";

const httpOptions : any    = {
  headers: new HttpHeaders({
    //'Content-Type':  'application/json',
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

export interface BookApi{
  title:string;
  subtitle:string;
  isbn13:string;
  price:string;
  image:string;
  url:string;
}

export interface Api{
  total:number;
  page:number;
  books: BookApi[];
}

export interface BookIsbn{
  error:number;
  title:string;
  subtitle:string;
  authors:string;
  publisher:string;
  isbn10:string;
  isbn13:string;
  pages:number;
  year:number;
  rating:number;
  desc:number;
  price:string;
  image:string;
  url:string;
  pdf:string;
}

interface HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
}


const books: Book[] = [{id:1,title:"Damn Daniel",author:"Daniel",genre:"Meme Culture",quantity:10,price:100},
  {id:2,title:"Damn Daniel2",author:"Daniel",genre:"Meme Culture",quantity:10,price:100}
  ];


@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})


export class LibraryComponent implements OnInit {
  constructor(private http: HttpClient, private router:Router, private route:ActivatedRoute) {
  }
  private apiServerUrl = environment.apiBaseUrl;
  dataSource = books;
  displayedColumns :string[] = ["id","title","author","genre","quantity","price","actions"];
  public selectBook: Book = {id:0,title:'',author:'',genre:'',quantity:0,price:0}
  generate(){
    this.http.get<any>(`${this.apiServerUrl}/books`).subscribe((response)=> {
        console.log(response)
        this.dataSource = response

      }
    )
  }


  create(){
    this.router.navigate(['bookEdit'],{relativeTo:this.route})
  }

  edit(){
    this.router.navigate(['bookEdit',JSON.stringify(this.selectBook)])
    console.log(this.selectBook.id)
  }

  sellBook(book:Book){
    let params = new HttpParams().set("id", book.id);
    this.http.patch(`${this.apiServerUrl}/books/sell/${book.id}`,{params}).subscribe((response)=>{
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

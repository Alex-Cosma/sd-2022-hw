import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";

import {Router} from "@angular/router";


export interface User{
  id:number;
  name:string;
  password:string;
  email:string;
}
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
}
const users: User[]=[{
  id:1,name:"User1",password:"password",email:"email@email.com"
}]
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  public user:User = {id:0,name:'',email:'',password:''}
  public selectUser:User = {id:0,name:'',email:'',password:''}
  constructor(private http: HttpClient, private router:Router) { }
  dataSource  =  users;
  displayedColumns: string[]=["id","username","password","email","actions"];
  private apiServerUrl = environment.apiBaseUrl;
  private editUrl = 'http://localhost:8080/api/users/edit'
  edit(){
    this.router.navigate(['userEdit',JSON.stringify(this.selectUser)])
  }
  generate(){

    this.http.get<any>(`${this.apiServerUrl}/users`).subscribe((response)=> {
        console.log(response)
        this.dataSource = response

      }
    )
  }

  deleteUser(user:User){
    let params = new HttpParams().set("id", user.id);
    this.http.delete(`${this.apiServerUrl}/users/delete/${user.id}`,{params}).subscribe((response)=>{
      this.dataSource = this.dataSource.splice(user.id,1);
      console.log("delete")
      this.generate()
    })
  }
  create(){
    this.router.navigate(['userEdit',JSON.stringify(this.selectUser)]);
  }

  goToBooks(){
    this.router.navigate(['booksAdmin']);
  }

  ngOnInit(): void {
    this.generate()
  }

}

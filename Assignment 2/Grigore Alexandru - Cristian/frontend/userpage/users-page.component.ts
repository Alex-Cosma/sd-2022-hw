import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";

export class User{
  constructor(
    public id: number,
    public name: string,
    public email: string,
    public roles: string[],
  ) {
  }
}
export class SignupRequest{
  constructor(
    public username: string,
    public email: string,
    public password: string,
    public roles: string[]
  ) {
  }
}

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent {

  signupRequest: SignupRequest = {username : "", email : "", password : "", roles : []}
  public userEdited: User = {id:0 , name:"", email:"", roles:[]}
  users: User[] = [];
  apiUrl = 'http://localhost:8088/api/user';
  sign_up = 'http://localhost:8088/api/auth/sign-up';
  editUrl = 'http://localhost:8088/api/user/';

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ){ }

  getUsers(){
    this.httpClient.get<any>(this.apiUrl).subscribe(
      (response) =>{
        console.log(response);
        this.users = response;
      }
    )
  }

  goToBooks(){
    this.router.navigate(['books']);
  }

  deleteUsers(user:any){
    let params = new HttpParams().set("id", user.id);
    this.httpClient.delete(`http://localhost:8088/api/user/${user.id}/delete`,{params}).subscribe(
      (response) =>{
        this.getUsers();
      }
    );
  }

  edit(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    this.userEdited.name = this.signupRequest.username;
    this.userEdited.email = this.signupRequest.email;
    this.httpClient.put(`http://localhost:8088/api/user/${this.userEdited.id}`,JSON.stringify(this.userEdited),httpOptions)
      .subscribe((data) => {
        console.log("user edited");
        this.getUsers();
      })
  }

  fill(user:User){
    this.userEdited = user
    this.signupRequest.username = this.userEdited.name
    this.signupRequest.email = this.userEdited.email
    console.log(this.userEdited);
  }

  register() {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    this.signupRequest.roles = ["REGULAR"];
    console.log(JSON.stringify(this.signupRequest));
    this.httpClient.post(this.sign_up,JSON.stringify(this.signupRequest),httpOptions).subscribe(data => {
      console.log("user created");
    });
    this.getUsers();
  }

  ngOnInit() {
    this.getUsers();
  }

}

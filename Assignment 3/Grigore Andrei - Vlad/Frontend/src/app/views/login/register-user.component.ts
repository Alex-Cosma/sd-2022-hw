import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../../user";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";
import {readSpanComment} from "@angular/compiler-cli/src/ngtsc/typecheck/src/comments";

export interface LoginRequest{
  username:string;
  password:string;
}

export interface SignupRequest {

  username: string;
  email:string;
  password:string;
  roles:string[];
}

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
}
@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})

@Injectable({
  providedIn: "root"
})

export class RegisterUserComponent implements OnInit {
  public logInReq:LoginRequest ={username:'',password:''};
  public signUpReq:SignupRequest ={username:'',password:'',email:'',roles:[]};

  private apiServerUrl = environment.apiBaseUrl;
  private signInUrl = `http://localhost:8080/api/auth/sign-in`;
  private signUpUrl =  `http://localhost:8080/api/auth/sign-up`;
  constructor(private http: HttpClient,private router:Router) { }
  signIn(){
    this.http.post<SignupRequest>(this.signInUrl,JSON.stringify(this.logInReq),httpOptions).subscribe((response)=>{
      console.log(response.roles)
      if(response.roles.find(r=>r=='ADMINISTRATOR')){
        this.router.navigate(['users'])
      }
      if(response.roles.find(r=>r=='EMPLOYEE')){
        this.router.navigate(['books'])
      }
    });

  }
  signUp(){
    this.signUpReq.roles=["EMPLOYEE"]
    this.http.post(this.signUpUrl,JSON.stringify(this.signUpReq),httpOptions).subscribe((response)=>{
      console.log("user created with username"+this.signUpReq.username)
    })
  }
  ngOnInit(): void {
  }
}

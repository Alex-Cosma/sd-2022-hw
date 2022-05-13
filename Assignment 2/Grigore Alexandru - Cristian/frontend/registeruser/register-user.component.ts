import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {coerceStringArray} from "@angular/cdk/coercion";
import {Router} from "@angular/router";

export class LogInResponse {
  constructor(
    public username: string,
    public password: string
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
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})

export class RegisterUserComponent implements OnInit {

  sign_in = 'http://localhost:8088/api/auth/sign-in';
  sign_up = 'http://localhost:8088/api/auth/sign-up';
  role : string = "";
  loginUserData: LogInResponse = {username : "", password : ""};
  signupRequest: SignupRequest = {username : "", email : "", password : "", roles : []}

  constructor(private httpClient: HttpClient, private router: Router) {
  }

  ngOnInit(): void {
  }

  logIn(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    this.httpClient.post<SignupRequest>(this.sign_in,JSON.stringify(this.loginUserData),httpOptions).subscribe(data => {
      console.log(data.roles);
      if(data.roles.find(r => r == 'ADMIN')){
        this.router.navigate(['users']);
      }else{
        this.router.navigate(['bookRegular'])
      }
    });
  }

  register() {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    this.signupRequest.roles = ["ADMIN"];
    console.log(JSON.stringify(this.signupRequest));
    this.httpClient.post(this.sign_up,JSON.stringify(this.signupRequest),httpOptions).subscribe(data => {
      console.log(data);
    });
  }
}

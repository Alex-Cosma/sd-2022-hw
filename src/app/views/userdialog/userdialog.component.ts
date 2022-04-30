import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";

export interface UserEdit{
  id:number;
  name:string;
  email:string;
  password:string;
  roles:string[];
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
  selector: 'app-userdialog',
  templateUrl: './userdialog.component.html',
  styleUrls: ['./userdialog.component.css']
})
export class UserdialogComponent implements OnInit {
  public selectUser: UserEdit ={id:0,name:'',password:'',email:'',roles:[]};
  public createUser: SignupRequest ={username:'',password:'',email:'',roles:[]};
  private editUrl=  `http://localhost:8080/api/users/edit`
  private signUpUrl =  `http://localhost:8080/api/auth/sign-up`;
  constructor(private http:HttpClient,private router:Router,private activatedRoute:ActivatedRoute) {
    this.selectUser = JSON.parse(activatedRoute.snapshot.params["selectUser"])
  }
  close(){
    this.router.navigate(['users'])
  }

  edit(){
    this.http.post(`${this.editUrl}/${this.selectUser.id}`,JSON.stringify(this.selectUser),httpOptions).subscribe((response)=>{
      console.log("User edited")
    })
  }
  signUp(){
    this.createUser.roles=["EMPLOYEE"]
    this.http.post(this.signUpUrl,JSON.stringify(this.createUser),httpOptions).subscribe((response)=>{
      console.log("user created with username"+this.createUser.username)
      this.router.navigate(['users'])
    })
  }

  ngOnInit(): void {
  }

}

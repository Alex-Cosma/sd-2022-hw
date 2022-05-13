import {Component, OnInit} from '@angular/core';
import {User} from "./user";
import {RegisterUserService} from "./views/login/register-user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Frontend';
  public users: User[] | undefined;

  constructor(private userService: RegisterUserService, private router:Router) {
  }

  public getUsers(): void {
    this.userService.getUsers();
  }

  ngOnInit(): void {
    this.router.navigate(['login'])
    this.getUsers();
  }
}

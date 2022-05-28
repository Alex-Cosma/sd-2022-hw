import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

export class Summoner{
  constructor(
    public id: number,
    public league: string,
    public name: string
  ) {
  }

}

@Component({
  selector: 'app-summoners-page',
  templateUrl: './summoners-page.component.html',
  styleUrls: ['./summoners-page.component.css']
})
export class SummonersPageComponent implements OnInit {
  apiUrl = "http://localhost:8080/api/summoners";
  summoners: Summoner[] = [];
  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) { }


  goToSearch(){
    this.router.navigate(["search"]);
  }

  getSummoners(){
    this.httpClient.get<any>(this.apiUrl).subscribe(
      (response) => {
        this.summoners = response;
        console.log(response);
      }
    )
  }

  ngOnInit(): void {
    this.getSummoners();
  }

}

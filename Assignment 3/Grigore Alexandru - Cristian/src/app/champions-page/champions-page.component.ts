import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {ActivatedRoute,provideRoutes, Router, ROUTES} from "@angular/router";

export interface Stat {
  id: number;
  stat: string;
  value: number;
}

export interface Item {
  id: number;
  name: string;
  stats: Stat[];

}

export class Champion {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public damage: number,
    public toughness: number,
    public crowdControl: number,
    public mobility: number,
    public utility: number,
    public image: string,
    public build: Item[]
  ) {
  }
}

@Component({
  selector: 'app-champions-page',
  templateUrl: './champions-page.component.html',
  styleUrls: ['./champions-page.component.css']
})


export class ChampionsPageComponent implements OnInit {
  champs: Champion[] = []
  apiUrl = 'http://localhost:8080/api/champs';
  public selectChampion : Champion={id:0,name:'',description:'',damage:0,toughness:0,crowdControl:0,mobility:0,utility:0,image:'',build:[]}
  constructor(
    private httpClient: HttpClient,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.getChamps();
  }

  getChamps(){
    this.httpClient.get<any>(this.apiUrl).subscribe(
      (response) =>{
        console.log(localStorage.getItem('currentUser'));
        this.champs = response;
      }
    )
  }

  editChamp(){
    this.router.navigate(['buildEdit',JSON.stringify(this.selectChampion)])
  }
  close(){
    this.router.navigate(['users'])
  }
  logOut() {
    this.router.navigate(['login'])
  }
}

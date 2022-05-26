import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {Region} from "../search-page/search-page.component";

export interface Summoner{
  id: number,
  league: string,
  name: string
  region: Region
}

export class LogInResponse {
  constructor(
    public id: number,
    public username: string,
    public password: string
  ) {
  }
}

@Component({
  selector: 'app-summoner-mastery',
  templateUrl: './summoner-mastery.component.html',
  styleUrls: ['./summoner-mastery.component.css']
})
export class SummonerMasteryComponent implements OnInit {
  public mastery: string[] = []
  public summoner: Summoner = {id:0,league:'',name:'',region:{id:0,region:''}}
  public masteryUrl = `http://localhost:8080/api/summoners/mastery`
  loginUserData: LogInResponse = {id:0,username : "", password : ""};
  constructor( private httpClient: HttpClient,
               private activatedRoute: ActivatedRoute,
               private router: Router) {
    this.summoner = JSON.parse(activatedRoute.snapshot.params["selectSummoner"])
  }

  getMastery(){
    this.httpClient.get<any>(`${this.masteryUrl}/${this.summoner.name}`).subscribe((response)=>{
      this.mastery = response
    })
  }

  close(){
    this.router.navigate(['login'])
  }
  ngOnInit(): void {
    this.getMastery()
  }

}

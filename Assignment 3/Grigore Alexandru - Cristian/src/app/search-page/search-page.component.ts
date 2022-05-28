import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import { saveAs } from 'file-saver';
import {map, Observable} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
export interface Rune {
  id: number;
  name: string;
  description: string;
}

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

export interface Region{
  id: number;
  region:string;
}

export interface Champion {
  id: number,
  name: string,
  description: string,
  damage: number,
  toughness: number,
  crowdControl: number,
  mobility: number,
  utility: number,
  image: string,
  build: string[],
  runes: string[]
}
export interface Summoner{
    id: number,
    league: string,
    name: string
    region: Region
}

export interface User{
  id:number;
  username: string;
  password:string;
}
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json',
    'Access-Control-Allow-Headers': 'Content-Type',
    'Access-Control-Allow-Methods': 'POST',
    'Access-Control-Allow-Origin': '*'})
}

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  private pdfUrl = `http://localhost:8080/api/champs/buildPDF`
  private champUrl = `http://localhost:8080/api/champs`
  private summonerUrl = `http://localhost:8080/api/summoners/filterName`
  private followUrl = `http://localhost:8080/api/users/follow`
  image: any;
  public build :string[] = []
  public runeBuild: string[]= []
  public champion: Champion = {id:0,name:'',description:'',damage:0,toughness:0,crowdControl:0,mobility:0,utility:0,image:'',build:[],runes:[]}
  public selectSummoner: Summoner = {id:0,league:'',name:'',region:{id:0,region:''}}
  public region : Region = {id:0, region:''}
  public user: User = {id:0, username:'',password:''}
  searchText: string = "";
  searchText2: string = ""
  socket = new SockJS('http://localhost:8080/darth-vader');
  constructor(
    private httpClient: HttpClient,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {this.user = JSON.parse(activatedRoute.snapshot.params["loginUserData"]) }

  searchChamp(){
    this.httpClient.get<any>(`${this.champUrl}/${this.searchText}`)
      .subscribe((response) =>{
        this.champion = response;
        this.build = this.champion.build
        this.runeBuild = this.champion.runes
        this.image = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' + btoa(this.champion.image));
        console.log(response)
      })
  }

  searchSummoner(){
    this.httpClient.get<any>(`${this.summonerUrl}/${this.searchText2}`)
      .subscribe((response) =>{
        this.selectSummoner = response;
        this.region = this.selectSummoner.region
        console.log(response)
      })
  }
  follow(){
    this.httpClient.post(`${this.followUrl}/${this.user.username}`,
      this.selectSummoner.name, httpOptions).subscribe(() => {
      console.log("summoner followed")
      this.router.navigate(['mastery',JSON.stringify(this.selectSummoner)])
    })
    this.connect();
  }

  exportPdf():Observable<Blob>{
    return this.httpClient.get(`http://localhost:8080/api/champs/buildPDF/${this.champion.name}`,{responseType : 'blob'});
  }

  generatePDF(){
    let headers = new HttpHeaders();
    headers = headers.set('Accept','application/pdf')
    this.exportPdf().subscribe((response) => {
      console.log(response);
      const blob = new Blob([response],{type : 'application/pdf'});
      const data = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = data;
      link.download = 'build.pdf';
      link.dispatchEvent(new MouseEvent('click', {bubbles:true, cancelable: true, view: window}));
    })
  }
  logOut(){
    this.router.navigate(['login'])
  }

  connect(){
    const stompClient = Stomp.over(this.socket);
    stompClient.connect({},function () {
      console.log("here")
      stompClient.subscribe(`/topic/user`, function (response: any) {
        //console.log("got it")
        console.log(response)
      });
    });
  }

  ngOnInit(): void {

  }

}

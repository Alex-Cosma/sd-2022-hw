import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

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

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json',
    'Access-Control-Allow-Headers': 'Content-Type',
    'Access-Control-Allow-Methods': 'POST',
    'Access-Control-Allow-Origin': '*'})
}

const items: Item[] = []
const runes: Rune[] = []
@Component({
  selector: 'app-champion-dialog',
  templateUrl: './champion-dialog.component.html',
  styleUrls: ['./champion-dialog.component.css']
})

export class ChampionDialogComponent implements OnInit {
  public selectChampion : Champion={id:0,name:'',description:'',damage:0,toughness:0,crowdControl:0,mobility:0,utility:0,image:'',build:[],runes:[]}
  private editBuildUrl = `http://localhost:8080/api/champs/edit`
  private getItemsUrl =  `http://localhost:8080/api/items`
  private getRunesUrl =  `http://localhost:8080/api/runes`
  private sendMailUrl = `http://localhost:8080/api/users/email`
  private pdfUrl = `http://localhost:8080/api/champs/buildPDF`
  public item1: string =''
  public item2: string =''
  public item3: string =''
  public item4: string =''
  public item5: string =''
  public item6: string =''
  public build: string[] =[]
  public rune1: string =''
  public rune2: string =''
  public rune3: string =''
  public rune4: string =''
  public rune5: string =''
  public rune6: string =''
  public rune7: string =''
  public runes: string[] =[]
  itemsData = items
  runesData = runes
  constructor(private http: HttpClient, private router: Router, private activatedRoute: ActivatedRoute) {
    this.selectChampion = JSON.parse(activatedRoute.snapshot.params["selectChampion"])
    console.log(this.selectChampion.name)
  }

  getItems(){
    this.http.get<any>(`${this.getItemsUrl}`).subscribe((response)=> {
        console.log(response)
        this.itemsData = response
      }
    )
  }

  getRunes(){
    this.http.get<any>(`${this.getRunesUrl}`).subscribe((response)=> {
        console.log(response)
        this.runesData = response
      }
    )
  }

  addBuild(){
    this.build = [this.item1,this.item2,this.item3,this.item4,this.item5,this.item6]
    this.selectChampion.build =  this.build
    this.runes = [this.rune1,this.rune2,this.rune3,this.rune4, this.rune5,this.rune6,this.rune7]
    this.selectChampion.runes = this.runes
  }

  async editBuild() {
    this.addBuild()
    this.http.post(`${this.editBuildUrl}/${this.selectChampion.id}`,
      JSON.stringify(this.selectChampion), httpOptions).subscribe(() => {
      console.log("build edited")
      this.router.navigate(['champs'])
    })
    await new Promise(f => setTimeout(f, 5000));
    this.sendMail();
  }

  sendMail(){
    this.http.post(`${this.sendMailUrl}`,JSON.stringify(this.selectChampion),httpOptions).subscribe(()=>{
      console.log("email sent");
    })
  }



  ngOnInit(): void {
    this.getItems()
    this.getRunes()
  }

}

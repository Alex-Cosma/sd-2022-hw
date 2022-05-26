import {NgModule} from "@angular/core";

//import {BookDialogComponent} from "./book-dialog/book-dialog.component";
import {RouterModule, Routes} from "@angular/router";
//import {BookPageComponent} from "./book-page/book-page.component";
import {RegisterUserComponent} from "./register-user/register-user.component";
import {ChampionsPageComponent} from "./champions-page/champions-page.component";
import {SearchPageComponent} from "./search-page/search-page.component";
import {SummonersPageComponent} from "./summoners-page/summoners-page.component";
import {ChampionDialogComponent} from "./champion-dialog/champion-dialog.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {SummonerMasteryComponent} from "./summoner-mastery/summoner-mastery.component";



const routes: Routes = [
  {path:'login', component:RegisterUserComponent},
  {path:'champs', component:ChampionsPageComponent},
  {path:'search/:loginUserData', component:SearchPageComponent},
  {path:'summoners',component: SummonersPageComponent},
  {path:'buildEdit/:selectChampion', component: ChampionDialogComponent},
  {path:'users', component: UserPageComponent},
  {path:'mastery/:selectSummoner', component: SummonerMasteryComponent}

];

@NgModule({
  imports:[RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutingModule{}
export const routingComponents = [ChampionsPageComponent,SearchPageComponent,SummonersPageComponent,ChampionDialogComponent]

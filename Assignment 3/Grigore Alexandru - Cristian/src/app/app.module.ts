import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterUserComponent } from './register-user/register-user.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatIconModule} from "@angular/material/icon";
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
//import {BookPageComponent} from './book-page/book-page.component';
import {MatTableModule} from "@angular/material/table";
//import { UsersPageComponent } from './users-page/users-page.component';
import {HttpClientModule} from "@angular/common/http";
//import { BookRegularComponent } from './book-regular/book-regular.component';
//import { BookDialogComponent } from './book-dialog/book-dialog.component';
import {AppRoutingModule} from "./app-routing.module";
import { ChampionsPageComponent } from './champions-page/champions-page.component';
import { ChampionDialogComponent } from './champion-dialog/champion-dialog.component';
import { SearchPageComponent } from './search-page/search-page.component';
import { SummonersPageComponent } from './summoners-page/summoners-page.component';
import {UserPageComponent} from "./user-page/user-page.component";
import { SummonerMasteryComponent } from './summoner-mastery/summoner-mastery.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterUserComponent,
    ChampionsPageComponent,
    ChampionDialogComponent,
    SearchPageComponent,
    SummonersPageComponent,
    UserPageComponent,
    SummonerMasteryComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MatInputModule,
    MatCardModule,
    MatTabsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,
    MatSelectModule,
    MatTableModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

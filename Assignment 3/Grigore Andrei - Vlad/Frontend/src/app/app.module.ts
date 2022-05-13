import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatIconModule} from '@angular/material/icon';
import {MatTabsModule} from '@angular/material/tabs';



import { AppComponent } from './app.component';
import { RegisterUserComponent } from './views/login/register-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule} from '@angular/common/http'
import {RegisterUserService} from "./views/login/register-user.service";
import { LibraryComponent } from './views/library/library.component';
import {MatTableModule} from "@angular/material/table";
import { UserComponent } from './views/user/user.component';
import { UserdialogComponent } from './views/userdialog/userdialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { BookDialogComponent } from './views/book-dialog/book-dialog.component';
import {AppRoutingModule} from "./app-routing.module";
import {LibraryAdminComponent} from "./views/libraryAdmin/libraryAdmin.component";


@NgModule({
  declarations: [
    AppComponent,
    RegisterUserComponent,
    LibraryComponent,
    LibraryAdminComponent,
    UserComponent,
    UserdialogComponent,
    BookDialogComponent
  ],
  imports: [
    HttpClientModule,
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
    MatTableModule,
    MatDialogModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [RegisterUserService],
  bootstrap: [AppComponent],
  entryComponents: [UserdialogComponent]
})
export class AppModule { }

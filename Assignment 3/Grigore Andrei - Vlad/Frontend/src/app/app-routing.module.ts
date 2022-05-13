import {NgModule} from "@angular/core";

import {UserdialogComponent} from "./views/userdialog/userdialog.component";
import {BookDialogComponent} from "./views/book-dialog/book-dialog.component";
import {RouterModule, Routes} from "@angular/router";
import {LibraryAdminComponent} from "./views/libraryAdmin/libraryAdmin.component";
import {LibraryComponent} from "./views/library/library.component"
import {UserComponent} from "./views/user/user.component";
import {RegisterUserComponent} from "./views/login/register-user.component";


const routes:Routes = [
  {path:'userEdit/:selectUser',component: UserdialogComponent},
  {path:'bookEdit/:selectBook',component: BookDialogComponent},
  {path:'booksAdmin',component: LibraryAdminComponent},
  {path:'books',component: LibraryComponent},
  {path:'users',component: UserComponent},
  {path:'login', component: RegisterUserComponent}
 ];

@NgModule({

  imports:[RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutingModule{}
export const routingComponents = [UserdialogComponent,BookDialogComponent,LibraryComponent,LibraryAdminComponent]

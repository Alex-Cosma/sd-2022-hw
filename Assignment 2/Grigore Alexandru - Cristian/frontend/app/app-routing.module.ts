import {NgModule} from "@angular/core";

import {BookDialogComponent} from "./book-dialog/book-dialog.component";
import {RouterModule, Routes} from "@angular/router";
import {BookPageComponent} from "./book-page/book-page.component";
import {RegisterUserComponent} from "./register-user/register-user.component";
import {BookRegularComponent} from "./book-regular/book-regular.component";
import {UsersPageComponent} from "./users-page/users-page.component";


const routes: Routes = [
  {path:'bookEdit', component:BookDialogComponent},
  {path:'books', component:BookPageComponent},
  {path:'login', component:RegisterUserComponent},
  {path:'bookRegular', component:BookRegularComponent},
  {path:'users', component:UsersPageComponent}
];

@NgModule({
  imports:[RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutingModule{}
export const routingComponents = [BookDialogComponent,BookPageComponent]

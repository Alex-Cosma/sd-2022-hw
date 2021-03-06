import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from "./authentication/components/register/register.component";
import { LoginComponent } from "./authentication/components/login/login.component";
import { AuthenticationGuard } from "./authentication/guards/authentication.guard";
import { VideosViewComponent } from './user/admin/components/videos/view/videos.component';
import { EmployeeVideosViewComponent } from './user/employee/components/videos-view/videos.component';
import { UsersViewComponent } from './user/admin/components/users/view/users.component';
import { AddUserComponent } from './user/admin/components/users/add/add-user.component';
import { EditUserComponent } from './user/admin/components/users/edit/edit-user.component';
import { AddVideoComponent } from './user/employee/components/add-video/add-video.component';
import { CreatePlaylistComponent } from './user/employee/components/create-playlist/create-playlist.component';
import { PlaylistsViewComponent } from './user/employee/components/playlists-view/playlists-view.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'admin/videos', component: VideosViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'employee', component: EmployeeVideosViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'employee/add-video', component: AddVideoComponent, canActivate: [AuthenticationGuard]},
  { path: 'employee/create-playlist', component: CreatePlaylistComponent, canActivate: [AuthenticationGuard]},
  { path: 'employee/playlists', component: PlaylistsViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/users', component: UsersViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/add-user', component: AddUserComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/users/:id', component: EditUserComponent, canActivate:[AuthenticationGuard],}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

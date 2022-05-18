import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from "./authentication/components/register/register.component";
import { LoginComponent } from "./authentication/components/login/login.component";
import { FormsModule } from "@angular/forms";
import { AuthenticationService } from "./api/services/authentication.service";
import { HttpClientModule } from "@angular/common/http";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule} from "@angular/router";
import { AuthenticationGuard} from "./authentication/guards/authentication.guard";
import { VideosViewComponent } from './user/admin/components/videos/view/videos.component';
import { DxButtonModule, DxDataGridModule, DxPopupModule } from "devextreme-angular";
import { VideoService } from "./api/services/video.service";
import { UsersViewComponent } from './user/admin/components/users/view/users.component';
import { EmployeeVideosViewComponent } from './user/employee/components/videos-view/videos.component';
import { UserService } from './api/services/user.service';
import { VideoToolbarComponent } from './user/admin/components/videos/toolbar/toolbar.component';
import { UserToolbarComponent } from './user/admin/components/users/toolbar/toolbar.component';
import { AddUserComponent } from './user/admin/components/users/add/add-user.component';
import { EditUserComponent } from './user/admin/components/users/edit/edit-user.component';
import { EmployeeToolbarComponent } from './user/employee/components/toolbar/employee-toolbar.component';
import { AddVideoComponent } from './user/employee/components/add-video/add-video.component';
import { CommentService } from './api/services/comment.service';
import { CreatePlaylistComponent } from './user/employee/components/create-playlist/create-playlist.component';
import { CreatePlaylistToolbarComponent } from './user/employee/components/create-playlist/toolbar/create-playlist-toolbar.component';
import { PlaylistService } from './api/services/playlist.service';
import { PlaylistsViewComponent } from './user/employee/components/playlists-view/playlists-view.component';
import { PlaylistToolbarComponent } from './user/employee/components/playlists-view/playlist-toolbar/playlist-toolbar.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    VideoToolbarComponent,
    VideosViewComponent,
    UsersViewComponent,
    EmployeeVideosViewComponent,
    UserToolbarComponent,
    AddUserComponent,
    EditUserComponent,
    EmployeeToolbarComponent,
    AddVideoComponent,
    CreatePlaylistComponent,
    CreatePlaylistToolbarComponent,
    PlaylistsViewComponent,
    PlaylistToolbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    RouterModule,
    DxDataGridModule,
    DxButtonModule,
    DxPopupModule
  ],
  providers: [
    AuthenticationService,
    VideoService,
    AuthenticationGuard,
    UserService,
    CommentService,
    PlaylistService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

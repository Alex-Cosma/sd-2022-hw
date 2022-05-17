import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Router} from "@angular/router";
import { VideoService } from "src/app/api/services/video.service";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";

@Component({
  selector: 'app-video-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class VideoToolbarComponent {

  public usernameSearchField: string = '';
  private urlPage: string = '/admin/users';
  @Output() getVideos = new EventEmitter();

  constructor(private videoService: VideoService,
    private router: Router) {
  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public getVideosFromUser(): void {
    this.videoService.getVideosFromUser(this.usernameSearchField).subscribe((videos:VideoDTO[]) => 
    this.getVideos.emit([videos, this.usernameSearchField]), 
    (error) => {
      alert("Can not found user with username " + this.usernameSearchField);
      this.getVideos.emit([[], '']);
    });
}

  public changePage(): void {
    this.router.navigate([this.urlPage]);
  }
}

import {Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import { VideoService } from "src/app/api/services/video.service";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";

@Component({
  selector: 'app-main-view',
  templateUrl : 'videos.component.html',
  styleUrls: ['videos.component.css']
})
export class VideosViewComponent{

  public videos: VideoDTO[] = [];
  public username: string = '';
  constructor(private router: Router,
              private videoService: VideoService) {
  }

  public getVideos(event: any[]): void {
    this.videos = event[0];
    this.username = event[1];
  }

  public deleteVideo(id: number): void {
    this.videoService.deleteVideo(id).subscribe(data =>  {
      alert(data.message);
      console.log(this.videos);
      this.videos = this.videos.filter(video => video.id !== id);
      console.log(this.videos)
    });
  }
}

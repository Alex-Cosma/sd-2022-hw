import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { VideoService } from "src/app/api/services/video.service";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";

@Component({
    selector: 'app-create-playlist',
    templateUrl: 'create-playlist.component.html',
    styleUrls: ['create-playlist.component.css']
})
export class CreatePlaylistComponent implements OnInit{

    public uploadedVideos: VideoDTO[] = [];
    public playlistVideos: VideoDTO[] = [];

    public constructor(private router: Router,
        private videoService: VideoService) {}
    public ngOnInit(): void {
        this.videoService.getUploadedVideos().subscribe(videos => this.uploadedVideos = videos);
    }

    public cancel(): void {
        this.router.navigate(['/employee']);
    }

    public addOnPlaylist(video: VideoDTO): void {
        this.playlistVideos.push(video);
        this.removeUploadedVideo(video);
    }

    public removeUploadedVideo(video: VideoDTO): void {
        const index: number = this.uploadedVideos.indexOf(video);
        if(index !== -1) {
            this.uploadedVideos.splice(index, 1);
        }
    }
}
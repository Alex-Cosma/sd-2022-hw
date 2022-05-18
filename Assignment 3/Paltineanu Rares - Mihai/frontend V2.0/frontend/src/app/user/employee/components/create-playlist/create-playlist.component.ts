import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { PlaylistService } from "src/app/api/services/playlist.service";
import { VideoService } from "src/app/api/services/video.service";
import { UserDetailsImpl } from "src/app/models/user-details-impl.model";
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
        private videoService: VideoService,
        private playlistService: PlaylistService) {}

    public ngOnInit(): void {
        this.videoService.getUploadedVideos().subscribe(videos => this.uploadedVideos = videos);
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

    public createPlaylist(): void {
        const user: UserDetailsImpl = JSON.parse(localStorage.getItem('user') ?? '{}', (key, value) => {
            if(key === "token" || key === "roles") {
                return undefined;
            }
            return value;
        });
        this.playlistService.create(user, this.playlistVideos).subscribe(() => {
            this.router.navigate(['/employee']);
        });
    }
}
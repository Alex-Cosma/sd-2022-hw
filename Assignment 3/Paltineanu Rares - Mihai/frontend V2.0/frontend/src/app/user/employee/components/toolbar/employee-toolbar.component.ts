import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Router } from "@angular/router";
import { PlaylistService } from "src/app/api/services/playlist.service";
import { VideoService } from "src/app/api/services/video.service";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";

@Component({
    selector: 'app-employee-toolbar',
    templateUrl: 'employee-toolbar.component.html',
    styleUrls: ['employee-toolbar.component.css']
})
export class EmployeeToolbarComponent{

    public usernameSearchField: string = '';
    @Output() getVideos = new EventEmitter();
    
    constructor(private router: Router,
        private videoService: VideoService,
        private playlistService: PlaylistService) {}

    public addVideo(): void {
        this.router.navigate(['/employee/add-video'])
    }
    
    public getVideosFromUser(): void {
        this.videoService.getVideosFromUser(this.usernameSearchField).subscribe((videos:VideoDTO[]) =>  {
            this.getVideos.emit([videos, this.usernameSearchField])
        });
    }

    public getVideosFromLoggedInUser(): void {
        const loggedInUser: any = JSON.parse(localStorage.getItem('user') ?? '{}');
        this.videoService.getVideosFromUser(loggedInUser.name).subscribe((videos:VideoDTO[]) => {
            this.getVideos.emit([videos, loggedInUser.name]);
        }, 
        (error) => {
            console.log(error)
        })
    }

    public getPlaylistsFromLoggedInUser(): void {
        const userId: number = JSON.parse(localStorage.getItem('user') ?? '{}')['id'];
        //@ts-ignore
        let playlists: Playlist;
        this.playlistService.getUserPlaylists(userId).subscribe(playlists => console.log(playlists));
        
    }

    public createPlaylist(): void {
        this.router.navigate(['/employee/create-playlist']);
    }

    public goToOwnPlaylists(): void {
        this.router.navigate(['/employee/playlists']);
    }

    public logout(): void {
        localStorage.setItem('user', '');
        this.router.navigate(['/login']);
    }
}
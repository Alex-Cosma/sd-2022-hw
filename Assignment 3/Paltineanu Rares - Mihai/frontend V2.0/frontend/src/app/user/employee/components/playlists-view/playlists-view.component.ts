import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { PlaylistService } from "src/app/api/services/playlist.service";
import { PlaylistDTO } from "src/app/models/playlist/dto/playlist-dto.model";

@Component({
    selector: 'app-playlists-view',
    templateUrl: 'playlists-view.component.html',
    styleUrls: ['playlists-view.component.css']
})
export class PlaylistsViewComponent implements OnInit{
    public playlists: PlaylistDTO[] = [];
    
    constructor(private router: Router,
        private playlistService: PlaylistService) {}

    public ngOnInit(): void {
        const userId: number = JSON.parse(localStorage.getItem('user') ?? '{\"id\": -1}')['id'];
        this.playlistService.getUserPlaylists(userId).subscribe((playlists: PlaylistDTO[]) => {
            this.playlists = playlists;
        })
    }
}
import { Component, EventEmitter, Output } from "@angular/core";
import { Router } from "@angular/router";

@Component({
    selector: 'app-create-playlist-toolbar',
    templateUrl: 'create-playlist-toolbar.component.html',
    styleUrls: ['create-playlist-toolbar.component.css']
})
export class CreatePlaylistToolbarComponent {
    @Output() createPlaylist = new EventEmitter();
    constructor(private router: Router) {}

    public cancel(): void {
        this.router.navigate(['/employee']);
    }

    public createPlaylistFunc(): void {
        this.createPlaylist.emit();
    }
}
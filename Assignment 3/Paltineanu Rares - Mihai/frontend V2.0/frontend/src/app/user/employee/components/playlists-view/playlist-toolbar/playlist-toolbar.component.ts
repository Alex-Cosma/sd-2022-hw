import { Component } from "@angular/core";
import { Router } from "@angular/router";


@Component({
    selector: 'app-playlist-toolbar',
    templateUrl: 'playlist-toolbar.component.html',
    styleUrls: ['playlist-toolbar.component.css']
})
export class PlaylistToolbarComponent {
    
    constructor(private router: Router) {}
    public goBack(): void {
        this.router.navigate(['/employee']);
    }
}
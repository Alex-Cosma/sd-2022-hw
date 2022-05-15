import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Router } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
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
        private videoService: VideoService) {}

    public addVideo(): void {
        this.router.navigate(['/employee/add-video'])
    }
    
    public getVideosFromUser(): void {
        this.videoService.getVideosFromUser(this.usernameSearchField).subscribe((videos:VideoDTO[]) => 
        this.getVideos.emit([videos, this.usernameSearchField]));
    }

    public getVideosFromLoggedInUser(): void {
        //@ts-ignore
        const loggedInUser: any = JSON.parse(localStorage.getItem('user'));
        console.log(loggedInUser.name)
        this.videoService.getVideosFromUser(loggedInUser.name).subscribe((videos:VideoDTO[]) => {
            //@ts-ignore
            this.getVideos.emit([videos, loggedInUser.name]);
        })
    }

    public logout(): void {
        localStorage.setItem('user', '');
        this.router.navigate(['/login']);
    }
}
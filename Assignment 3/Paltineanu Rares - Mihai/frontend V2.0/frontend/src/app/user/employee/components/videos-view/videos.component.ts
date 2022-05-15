import { Component, OnInit,  } from "@angular/core";
import { Router } from "@angular/router";
import { VideoService } from "src/app/api/services/video.service";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";


@Component({
    selector: 'app-employee-videos',
    templateUrl: 'videos.component.html',
    styleUrls: ['videos.component.css']
})
export class EmployeeVideosViewComponent implements OnInit{

    public videos: VideoDTO[] = [];
    public username: string = '';
    constructor(private router: Router,
        private videoService: VideoService) {}

    public ngOnInit(): void {
    }

    public getVideos(event: any[]): void {
        this.videos = event[0];
        this.username = event[1];
        console.log(event[1])
    }
}
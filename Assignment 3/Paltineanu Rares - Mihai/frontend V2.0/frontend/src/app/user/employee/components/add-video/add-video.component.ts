import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { VideoService } from "src/app/api/services/video.service";

@Component({
    selector: 'app-add-video',
    templateUrl: './add-video.component.html',
    styleUrls: ['./add-video.component.css']
})
export class AddVideoComponent {
    public numbers: number[];
    public maxResults: number = 0;
    public query: string = '';
    public videos: any[] = [];
    constructor(private router: Router,
        private videoService: VideoService) {
        this.numbers = Array.from(Array(100).keys());
    }

    public getVideos(): void {
        this.videoService.getVideos(this.query, this.maxResults).subscribe(videos => {console.log(videos.items[0])
        })
    }

    public onCancel(): void {
        this.router.navigate(['/employee']);
    }
}
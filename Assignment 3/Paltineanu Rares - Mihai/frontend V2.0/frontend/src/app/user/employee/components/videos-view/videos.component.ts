import { Component, OnDestroy, OnInit,  } from "@angular/core";
import { Router } from "@angular/router";
import { CommentService } from "src/app/api/services/comment.service";
import { VideoService } from "src/app/api/services/video.service";
import { UserDetailsImpl } from "src/app/models/user-details-impl.model";
import { User } from "src/app/models/user.model";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";

@Component({
    selector: 'app-employee-videos',
    templateUrl: 'videos.component.html',
    styleUrls: ['videos.component.css']
})
export class EmployeeVideosViewComponent {

    public videos: VideoDTO[] = [];
    public username: string = '';
    public commentInput: string = '';

    constructor(private router: Router,
        private videoService: VideoService,
        private commentService: CommentService) {}

    public getVideos(event: any[]): void {
        this.videos = event[0];
        this.username = event[1];
    }

    public reportVideo(id: number): void {
    }

    public addComment(video: VideoDTO): void {
        const user: UserDetailsImpl = JSON.parse(localStorage.getItem('user') ?? '{}', (key, value) => {
            if(key === "token" || key === "roles") {
                return undefined;
            }
            return value;
        });
        this.commentService.addComment(this.commentInput, user, video).subscribe(() =>  {
                this.commentInput = '' 
                this.videoService.getVideosFromUser(this.username).subscribe(videos => {
                    this.videos = videos;
                })
            }    
        );
    }
}
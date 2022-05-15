import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { VideoService } from "src/app/api/services/video.service";
import { UserDetailsImpl } from "src/app/models/user-details-impl.model";
import { ResultDTO } from "src/app/models/video/result-dto.model";
import { Video } from "src/app/models/video/video.model";

@Component({
    selector: 'app-add-video',
    templateUrl: './add-video.component.html',
    styleUrls: ['./add-video.component.css']
})
export class AddVideoComponent implements OnInit{
    public numbers: number[];
    public maxResults: number = 0;
    public query: string = '';
    public videos: ResultDTO = new ResultDTO();
    public popupVisible: boolean = false;
    public popupButtons: any;
    //@ts-ignore
    public currentVideo: VideoDTO;

    constructor(private router: Router,
        private videoService: VideoService) {
        this.numbers = Array.from(Array(100).keys());
    }

    ngOnInit(): void {
        this.popupButtons = [
            {
                toolbar: 'bottom',
                widget: 'dxButton',
                location: 'after',
                options: {
                    disabled: false,
                    text: 'Upload video',
                    elementAttr: { class: 'mf-blue-btn-primary' },
                    onClick: () => {
                        this.uploadVideo();
                        this.onClose();
                    }
                }
            },
            {
                toolbar: 'bottom',
                widget: 'dxButton',
                location: 'after',
                options: {
                    text: 'Cancel',
                    disabled: false,
                    onClick: () => {
                        this.onClose();
                    }
                }
            }
        ]
    }

    public uploadVideo(): void {
        //@ts-ignore
        const user: UserDetailsImpl = JSON.parse(localStorage.getItem('user'), (key, value) => {
            if(key === "token" || key === "roles") {
                return undefined;
            }
            return value;
        });
        this.videoService.uploadVideo(this.currentVideo, user).subscribe(
            () => this.router.navigate(['/employee'])
        );
    }

    public showVideoInfo(video: Video) {
        this.currentVideo = video;
        this.popupVisible = true;
    }

    public getVideos(): void {
        this.videoService.getVideos(this.query, this.maxResults).subscribe(videos => {
            if(videos != null) {
                this.videos = videos;
            }
        })
    }

    public onCancel(): void {
        this.router.navigate(['/employee']);
    }

    private onClose(): void {
        this.popupVisible = false;
    }

    public closeOnOutsideClick = (e: any) => {
        if(e.target.className.includes('add-video-popup') === true) {
            this.onClose();
        }
    }
}
import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
import { VideoService } from "src/app/api/services/video.service";


@Component({
    selector: 'app-employee-videos',
    templateUrl: 'videos.component.html',
    styleUrls: ['videos.component.css']
})
export class EmployeeVideosViewComponent implements OnInit{
    //@ts-ignore
    @ViewChild(DxDataGridComponent) grid: DxDataGridComponent;

    constructor(private router: Router,
        private videoService: VideoService) {}

    public ngOnInit(): void {
    }
}
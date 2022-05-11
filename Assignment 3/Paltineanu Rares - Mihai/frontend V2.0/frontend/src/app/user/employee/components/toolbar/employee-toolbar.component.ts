import { Component, Input, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
import { VideoService } from "src/app/api/services/video.service";

@Component({
    selector: 'app-employee-toolbar',
    templateUrl: 'employee-toolbar.component.html',
    styleUrls: ['employee-toolbar.component.css']
})
export class EmployeeToolbarComponent{

    //@ts-ignore
    @Input() grid: DxDataGridComponent;
    public quantity: number = 0;
    public filter: string = '';
    constructor(private router: Router,
        private videoService: VideoService) {}

    public addVideo(): void {
        this.router.navigate(['/employee/add-video'])
    }
    
    public logout(): void {
        localStorage.setItem('user', '');
        this.router.navigate(['/login']);
    }
}
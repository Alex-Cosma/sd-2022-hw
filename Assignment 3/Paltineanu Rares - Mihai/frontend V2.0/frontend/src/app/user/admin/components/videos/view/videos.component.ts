import {Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import { VideoService } from "src/app/api/services/video.service";

@Component({
  selector: 'app-main-view',
  templateUrl : 'videos.component.html',
  styleUrls: ['videos.component.css']
})
export class VideosViewComponent implements OnInit, OnDestroy{

  constructor(private router: Router,
              private videoService: VideoService) {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }
}

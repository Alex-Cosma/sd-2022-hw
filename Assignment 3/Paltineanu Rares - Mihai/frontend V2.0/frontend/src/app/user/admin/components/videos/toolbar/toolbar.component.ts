import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import { VideoService } from "src/app/api/services/video.service";

enum ReportType{
  CSV = 'CSV',
  PDF = 'PDF'
}

@Component({
  selector: 'app-video-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class VideoToolbarComponent {

  buttonName: string = 'View users';

  urlPage: string = '/admin/users';

  constructor(private videoService: VideoService,
    private router: Router) {
  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }
  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  public changePage(): void {
    this.router.navigate([this.urlPage]);
  }
}

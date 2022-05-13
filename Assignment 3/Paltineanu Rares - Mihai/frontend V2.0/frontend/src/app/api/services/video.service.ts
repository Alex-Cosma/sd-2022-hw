import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {authHeader} from "../authentication/http";
import { VIDEOS_URL } from "src/app/http/http-urls.component";
import { ResultDTO } from "src/app/models/video/result-dto.model";
import { VideoDTO } from "src/app/models/video/video-dto.model";
import { UserDetailsImpl } from "src/app/models/user-details-impl.model";

@Injectable()
export class VideoService {
  constructor(private http: HttpClient) {}

  public getVideos(query: string, maxResults: number): Observable<ResultDTO> {
    const headers = authHeader()
    const url: string = VIDEOS_URL + `/get-videos?key=AIzaSyDhfMBuo6UdD4x2327O7sDT7BKbYuMzb20&q=${query}&type=video&part=snippet&maxResults=${maxResults}`;
    return this.http.get<ResultDTO>(url, {headers})
  }

  public uploadVideo(video: VideoDTO, user: UserDetailsImpl): Observable<any> {
    const headers = authHeader();
    const url: string = VIDEOS_URL + '/upload-video';
    return this.http.post(url, {video: video, user: user}, {headers});
  }
}

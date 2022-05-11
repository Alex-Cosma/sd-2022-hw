import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {authHeader} from "../authentication/http";
import { VIDEOS_URL } from "src/app/http/http-urls.component";

@Injectable()
export class VideoService {
  constructor(private http: HttpClient) {}

  public getVideos(query: string, maxResults: number): Observable<any> {
    const headers = authHeader()
    const url: string = VIDEOS_URL + `/get-videos?key=AIzaSyDhfMBuo6UdD4x2327O7sDT7BKbYuMzb20&q=${query}&type=video&snippet=snippet&maxResults=${maxResults}`;
    console.log(url)
    return this.http.get<any>(url, {headers})
  }
}

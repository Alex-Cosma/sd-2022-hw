import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PLAYLIST_URL } from "src/app/http/http-urls.component";
import { PlaylistDTO } from "src/app/models/playlist/dto/playlist-dto.model";
import { UserDetailsImpl } from "src/app/models/user-details-impl.model";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";
import { authHeader } from "../authentication/http";

@Injectable()
export class PlaylistService {
    constructor(private http: HttpClient) {}

    public create(user: UserDetailsImpl, videos: VideoDTO[]): Observable<any> {
        const headers = authHeader();
        const url: string = PLAYLIST_URL + '/create-playlist';
        return this.http.post<any>(url, {user: user, videos: videos}, {headers});
    }

    public getUserPlaylists(id: number): Observable<PlaylistDTO[]> {
        const headers = authHeader();
        const url: string = PLAYLIST_URL + `/get-user-playlist/${id}`
        return this.http.get<PlaylistDTO[]>(url, {headers});
    }
}
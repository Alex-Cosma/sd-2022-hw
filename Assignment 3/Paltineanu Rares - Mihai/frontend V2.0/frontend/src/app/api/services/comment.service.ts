import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { COMMENT_URL } from "src/app/http/http-urls.component";
import { UserDetailsImpl } from "src/app/models/user-details-impl.model";
import { VideoDTO } from "src/app/models/video/dto/video-dto.model";
import { authHeader } from "../authentication/http";

@Injectable()
export class CommentService {

    constructor(private http: HttpClient) {}
    
    public addComment(content: string, user: UserDetailsImpl, video: VideoDTO): Observable<any> {
        const headers = authHeader();
        const url: string = COMMENT_URL + '/add-comment';
        return this.http.post<any>(url, {content: content, user: user, video: video}, {headers});
    }
}
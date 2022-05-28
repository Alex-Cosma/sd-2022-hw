import { Snippet } from "../../snippet.model";
import { VideoID } from "../../video-id.model";

export class VideoAPIDTO {
    id?: VideoID;
    snippet?: Snippet;
}
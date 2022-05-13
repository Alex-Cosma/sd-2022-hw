import { Snippet } from "./snippet.model";
import { VideoID } from "./video-id.model";

export class VideoDTO {
    id?: VideoID;
    snippet?: Snippet;
}
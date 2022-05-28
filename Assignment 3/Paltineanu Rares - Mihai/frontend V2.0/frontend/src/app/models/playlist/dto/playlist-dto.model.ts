import { User } from "../../user.model";
import { VideoDTO } from "../../video/dto/video-dto.model";

export class PlaylistDTO {
    id?: number;
    videos?: VideoDTO[];
    user?: User;
}
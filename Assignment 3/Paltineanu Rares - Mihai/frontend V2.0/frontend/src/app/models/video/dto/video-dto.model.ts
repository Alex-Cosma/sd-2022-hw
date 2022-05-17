import { CommentDTO } from "../../comment/dto/comment-dto.model";
import { User } from "../../user.model";

export class VideoDTO {
    id?: number;
    title?: string;
    thumbnailUrl?: string;
    description?: string;
    channelTitle?: string;
    videoId?: string;
    user?: User;
    comments?: CommentDTO[];
}
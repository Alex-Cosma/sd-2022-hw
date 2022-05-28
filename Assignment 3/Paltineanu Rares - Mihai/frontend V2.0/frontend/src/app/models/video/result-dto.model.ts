import { VideoAPIDTO } from "./dto/api/video-api-dto.model";

export class ResultDTO {
    items?: VideoAPIDTO[];

    constructor() {
        this.items = [];
    }
}
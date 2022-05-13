import { VideoDTO } from "./video-dto.model";

export class ResultDTO {
    items?: VideoDTO[];

    constructor() {
        this.items = [];
    }
}
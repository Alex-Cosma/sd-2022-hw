import { Video } from "./video.model";

export class ResultDTO {
    items?: Video[];

    constructor() {
        this.items = [];
    }
}
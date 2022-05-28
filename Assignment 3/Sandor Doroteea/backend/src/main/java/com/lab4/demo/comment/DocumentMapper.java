package com.lab4.demo.comment;

import com.lab4.demo.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
@RequiredArgsConstructor
public class DocumentMapper {

    public  Document convertToDocument(Comment comment) {
        if(comment.getId() != null) {
            return new Document("_id", new Document("$oid",new Document("content", comment.getContent()).append("user_id", comment.getUserId()).append("track_id", comment.getTrackId())));
        }
        return new Document("content", comment.getContent()).append("user_id", comment.getUserId()).append("track_id", comment.getTrackId());
    }
    public  Comment convertFromDocument(Document document) {
        Comment comment = new Comment();
        //System.out.println(document.getObjectId("_id").toString());
        comment.setId(document.getObjectId("_id").toString());
        comment.setContent(document.getString("content"));
        comment.setUserId(document.getLong("user_id"));
        comment.setTrackId(document.getLong("track_id"));
        return comment;
        //return new Comment("content", comment.getContent()).append("user_id", comment.getUserId()).append("track_id", comment.getTrackId());
    }
}

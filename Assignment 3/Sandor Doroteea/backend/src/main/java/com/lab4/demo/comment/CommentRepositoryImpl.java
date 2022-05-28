package com.lab4.demo.comment;

import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.mongoDatabase.MongoDB;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    protected final MongoDatabase database;
    protected final MongoCollection<Document> col;
    private DocumentMapper mapper = new DocumentMapper();

    public CommentRepositoryImpl() {
        this.database = MongoDB.instance().getDatabase();;
        this.col = MongoDB.instance().getCollection();
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        for (Document document : col.find()) {
            comments.add(mapper.convertFromDocument(document));
        }
        return comments;
    }

    @Override
    public Comment findById(String id) {
        return mapper.convertFromDocument(col.find(eq("_id", new ObjectId(id))).first());
    }

    @Override
    public Comment save(Comment comment) {
        col.insertOne(mapper.convertToDocument(comment));
        Comment savedComment = mapper.convertFromDocument(Objects.requireNonNull(col.find(mapper.convertToDocument(comment)).first()));
        return savedComment;
    }

    @Override
    public void delete(String id) {
        col.deleteOne(col.find(eq("_id", new ObjectId(id))).first());
    }

    @Override
    public Comment update(String id, Comment comment) {
        Document updateFields = new Document();
        updateFields.append("content", comment.getContent());

        Document setQuery = new Document();
        setQuery.append("$set", updateFields);

        Document searchQuery = new Document("_id", new ObjectId(id));

        col.updateOne(searchQuery, setQuery);
        return findById(id);
    }

    @Override
    public void deleteAll() {
        col.drop();
    }

}

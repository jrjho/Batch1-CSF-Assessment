package ibf2022.batch1.csf.assessment.server.models;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private String name;
    private String title;
    private int rating;
    private String comment;

    public static Document toDocument(Comment comment) {

        Document doc = new Document();
        
        doc.append("name", comment.getName())
            .append("title", comment.getTitle())
            .append("rating", comment.getRating())
            .append("comment", comment.getComment());

        return doc;
    }
    
}

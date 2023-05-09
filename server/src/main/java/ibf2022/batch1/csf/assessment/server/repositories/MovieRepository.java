package ibf2022.batch1.csf.assessment.server.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch1.csf.assessment.server.models.Comment;

@Repository
public class MovieRepository {

	private static final String C_COMMENTS = "comments";

	@Autowired
    MongoTemplate mongoTemplate;
	// TODO: Task 5
	// You may modify the parameter but not the return type
	// Write the native mongo database query in the comment below
	//
	public int countComments(String title) {
		//count number of title fields in comment in mongodb
		Query query = Query.query(Criteria.where("title").is(title));
        int result =  (int)mongoTemplate.count(query, C_COMMENTS);
        System.out.println("No. of comments: "+ result);
        return result;
	}

	// TODO: Task 8
	// Write a method to insert movie comments comments collection
	// Write the native mongo database query in the comment below
	//
	public void postComment(Comment comment) {

        Document doc = Comment.toDocument(comment);
        mongoTemplate.insert(doc, C_COMMENTS);

    }
}

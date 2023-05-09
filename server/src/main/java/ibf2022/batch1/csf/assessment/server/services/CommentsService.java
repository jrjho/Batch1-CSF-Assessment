package ibf2022.batch1.csf.assessment.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch1.csf.assessment.server.models.Comment;
import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;

@Service
public class CommentsService {
    
    @Autowired
    MovieRepository mRepo;

    public void saveComment(Comment comment) {
        
        mRepo.postComment(comment);
    }
}

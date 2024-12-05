package in.main.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.entity.Comment;
import in.main.entity.Post;
import in.main.repository.CommentRepository;
import in.main.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService{
	
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public Comment createComment(Long id, String postedBy, String content) {
		Post post=postRepository.findById(id).get();
		if(!ObjectUtils.isEmpty(post)) {
			Comment comment=new Comment();
			comment.setPost(post);
			comment.setContent(content);
			comment.setPostedBy(postedBy);
			comment.setCreatedAt(new Date());
			
			return commentRepository.save(comment);
		}
		
		throw new EntityNotFoundException("post not found");
	}

	@Override
	public List<Comment> getCommentsByPostId(Long id) {
		
		return commentRepository.findByPostId(id);
	}

	@Override
	public Boolean deleteComment(Long id) {
		Comment comment=commentRepository.findById(id).get();
		if(!ObjectUtils.isEmpty(comment)) {
			commentRepository.delete(comment);
			return true;
		}
		return false;
	}

	
	
	
	
	

}

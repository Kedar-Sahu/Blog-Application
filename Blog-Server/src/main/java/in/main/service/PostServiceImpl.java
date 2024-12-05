package in.main.service;

import in.main.entity.Post;
import in.main.repository.PostRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post post1=postRepository.findById(id).get();
        post1.setName(post.getName());
        post1.setContent(post.getContent());
        post1.setPostedBy(post.getPostedBy());
        post1.setImg(post.getImg());
        post1.setDate(new Date());
        return postRepository.save(post1);
    }

    @Override
    public Boolean deletePost(Long id) {
        Post post1=postRepository.findById(id).get();
        if (post1 != null){
            postRepository.delete(post1);
            return true;
        }
        return false;
    }

    @Override
    public void updateLikeCount(Long id) {
        Post post=postRepository.findById(id).get();
        post.setLikeCount(post.getLikeCount()+1);
        postRepository.save(post);
    }

    @Override
    public void updateViewCount(Long id) {
        Post post=postRepository.findById(id).get();
        post.setViewCount(post.getViewCount()+1);
        postRepository.save(post);
    }
    
    @Override
	public Boolean existPost(String name) {
		
		return postRepository.existsByName(name);
	}
    
    @Override
	public List<Post> searchByName(String name) {
    	return  postRepository.findAllByNameContaining(name);
	}
               
    public void removeSessionMessage() {
		HttpSession session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("succMsg");
		session.removeAttribute("errorMsg");
	}

	
}

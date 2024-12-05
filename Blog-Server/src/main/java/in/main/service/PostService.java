package in.main.service;

import in.main.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost(Post post);
    
    List<Post> getAllPost();
    
    Post getPost(Long id);
    
    Post updatePost(Long id,Post post);
    
    Boolean deletePost(Long id);
    
    void updateLikeCount(Long id);
    
    void updateViewCount(Long id);
    
    Boolean existPost(String name);
    
    List<Post> searchByName(String name);

}

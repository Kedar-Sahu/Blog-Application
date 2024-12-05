package in.main.repository;

import in.main.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
	
	Boolean existsByName(String name);
	
	List<Post> findAllByNameContaining(String name);
}

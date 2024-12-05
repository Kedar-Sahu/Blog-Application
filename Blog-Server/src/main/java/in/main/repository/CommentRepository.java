package in.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.main.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

	List<Comment> findByPostId(Long id);
}

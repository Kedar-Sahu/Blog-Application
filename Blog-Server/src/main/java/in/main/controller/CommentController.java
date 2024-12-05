package in.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.main.entity.Comment;
import in.main.entity.Post;
import in.main.service.CommentService;
import in.main.service.PostService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment/")
public class CommentController {

	

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;
	
	
	@PostMapping("/saveComment")
	public String createComment(@RequestParam Long id,@RequestParam String postedBy,@RequestParam String content,HttpSession session){
		Comment comment=commentService.createComment(id, postedBy, content);
		if(ObjectUtils.isEmpty(comment)) {
			session.setAttribute("errorMsg","Not saved ! internal server error");
			return "redirect:/getPost/" + id;
		} 
		return "redirect:/getPost/" + id;
	}
	
	
	@GetMapping("/getAllComment/{id}")
	public String getAllComment(Model model,@PathVariable Long id) {
		
		List<Comment> listComment=commentService.getCommentsByPostId(id);
		model.addAttribute("listComment", listComment);
		 Post post = postService.getPost(id);
		    model.addAttribute("post", post);
		    return "showPost";
	}
	
	
	
	
	
}

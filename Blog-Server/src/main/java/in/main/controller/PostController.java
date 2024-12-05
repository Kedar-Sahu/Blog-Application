package in.main.controller;

import in.main.entity.Post;

import in.main.service.PostService;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class PostController {

    @Autowired
    private PostService postService;
    
    @GetMapping("/")
	public String home() {
		return "home";
	}
    
    @GetMapping("/getAllPost")
   	public String getPosts(Model model) {
       	List<Post> postList=postService.getAllPost();
       	model.addAttribute("postList", postList);
   		return "showAllPost";
   	}
    
    
    @GetMapping("/getPost/{id}")
   	public String getPost(Model model,@PathVariable Long id) {
    	Post post=postService.getPost(id);
    	model.addAttribute("post", post);
   		return "showPost";
   	}
    
    @GetMapping("/getPost2/{id}")
   	public String getPost2(Model model,@PathVariable Long id) {
    	Post post=postService.getPost(id);
    	model.addAttribute("post", post);
   		return "showPost";
   	}
    
    @GetMapping("/createNewPost")
   	public String createPost() {
   		return "createPost";
   	}
    
    @PostMapping("/save")
    public String savePost(@ModelAttribute Post post,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
    	
    	String imageName=file!=null ? file.getOriginalFilename() : "default.jpg";
		post.setImg(imageName);
		
		if(postService.existPost(post.getName())) {
			session.setAttribute("errorMsg","post name already exists");
		}
		else {
			Post savePost=postService.savePost(post);
			if(ObjectUtils.isEmpty(savePost)) {
				session.setAttribute("errorMsg","Not saved ! internal server error");
			}
			else {
				File saveFile=new ClassPathResource("/static").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+"images"+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				
				session.setAttribute("succMsg","Saved successfully");
			}
		}
    	
    	return "redirect:/createNewPost";
    }
 
    
    
    @GetMapping("/loadUpdatePost/{id}")
   	public String updatePost(Model model,@PathVariable Long id) {
    	Post post1=postService.getPost(id);
    	model.addAttribute("post", post1);
   		return "updatePost";
   	}
    
    
    @PostMapping("/update")
	public String updateCategory(@ModelAttribute Post post,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		Post oldPost=postService.getPost(post.getId());
		String imageName=file.isEmpty() ?  oldPost.getImg() : file.getOriginalFilename();
		
		if(!ObjectUtils.isEmpty(post)) {
			oldPost.setName(post.getName());
			oldPost.setContent(post.getContent());
			oldPost.setPostedBy(post.getPostedBy());
			oldPost.setImg(imageName);
			oldPost.setDate(new Date());
		}
		
		Post updatePost=postService.updatePost(oldPost.getId(), oldPost);
		if(!ObjectUtils.isEmpty(updatePost)) {
			
			if(!file.isEmpty()) {
				File saveFile=new ClassPathResource("static").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+"images"+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("succMsg","Post update successfully");
		}
		else {
			session.setAttribute("errorMsg","something wrong on server");
		}
		return "redirect:/loadUpdatePost/" + post.getId();
	}
    
    
    @GetMapping("/updateLike/{id}")
    public String updateLike(@PathVariable Long id) {
    	postService.updateLikeCount(id);
    	return "redirect:/getAllPost";
    }
    
    @GetMapping("/updateLike2/{id}")
    public String updateLike2(@PathVariable Long id) {
    	postService.updateLikeCount(id);
    	return "redirect:/getPost/" + id;
    }
    
    @GetMapping("/updateLike3/{id}")
    public String updateLike3(@PathVariable Long id) {
    	postService.updateLikeCount(id);
    	return "redirect:/getPost/" + id;
    }
    
    
    @GetMapping("/updateView/{id}")
    public String updateView(@PathVariable Long id) {
    	postService.updateViewCount(id);
    	return "redirect:/getPost/" + id;
    }
    
    
    @GetMapping("/updateView2/{id}")
    public String updateView2(@PathVariable Long id) {
    	postService.updateViewCount(id);
    	return "redirect:/getPost/" + id;
    }
    
    
    @GetMapping("/deletePost/{id}")
    public String deletePst(@PathVariable Long id,HttpSession session) {
    	Boolean delete =postService.deletePost(id);
		if(delete) {
			session.setAttribute("succMsg","Post Delete Successfully");
			return "redirect:/getAllPost";
		}
		else {
			session.setAttribute("errorMsg","Something Wrong On Server");
			return "redirect:/getPost" + id;
		}
    }
    
    
    @GetMapping("/home")
    public String search() {
    	return "home";
    }
    
    
    @PostMapping("/saveSearch")
    public String search(@RequestParam("name") String name, Model model) {
        List<Post> results = postService.searchByName(name);
        model.addAttribute("postList", results);
        return "search"; 
    }
    
   

}

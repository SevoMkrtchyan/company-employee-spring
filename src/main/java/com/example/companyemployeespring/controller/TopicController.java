package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CommentService;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final CommentService commentService;

    @GetMapping("/topic")
    public String topic(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Topic> companyId = topicService.findAllByEmployee_Company_Id(currentUser.getEmployee().getCompany().getId());
        if (companyId.isEmpty()) {
            modelMap.addAttribute("msg", "Here are no topics, do you want to add it?");
        } else {
            modelMap.addAttribute("allTopics", companyId);
        }
        modelMap.addAttribute("topic", new Topic());
        return "topic";
    }

    @PostMapping("/createTopic")
    public String createTopic(@AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute Topic topic) {
        topic.setEmployee(currentUser.getEmployee());
        topic.setCreatedDate(new Date(System.currentTimeMillis()).toString());
        topicService.save(topic);
        return "redirect:/topic";
    }

    @GetMapping("/singleTopic")
    public String singleTopicPage(@RequestParam("id") int id, ModelMap modelMap) {
        Topic oneById = topicService.findOneById(id);
        List<Comment> allByTopic = commentService.findAllByTopic(oneById);
        if (allByTopic.isEmpty()) {
            modelMap.addAttribute("msg", "there are no comment in this topic, say anything");
        } else {
            modelMap.addAttribute("comments", allByTopic);
        }
        modelMap.addAttribute("singleTopic", oneById);
        modelMap.addAttribute("newComment", new Comment());
        return "singleTopic";
    }

    @PostMapping("/addComment")
    public String addComment(@AuthenticationPrincipal CurrentUser currentUser, @RequestParam("comment") String comment, @RequestParam("topicId") int topicId) {
        commentService.save(Comment.builder().
                topic(topicService.findOneById(topicId))
                .employee(currentUser.getEmployee())
                .createdDate(new Date(System.currentTimeMillis()).toString())
                .comment(comment)
                .build());
        return "redirect:/singleTopic?id=" + topicId;
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("id") int id, @RequestParam("topicId") int topicId, @AuthenticationPrincipal CurrentUser currentUser) {
        commentService.delete(id, currentUser.getEmployee());
        return "redirect:/singleTopic?id=" + topicId;
    }

}

package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CommentService;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Opened Topic Page with User {} {} id = {} ",
                currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId());
        return "topic";
    }

    @PostMapping("/createTopic")
    public String createTopic(@AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute Topic topic) {
        topic.setEmployee(currentUser.getEmployee());
        topic.setCreatedDate(new Date(System.currentTimeMillis()).toString());
        topicService.save(topic);
        log.info("Created Topic with name {} by User {} {} id = {}",
                topic.getContent(), currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId());
        return "redirect:/topic";
    }

    @GetMapping("/singleTopic")
    public String singleTopicPage(@RequestParam("id") int id, ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        Topic oneById = topicService.findOneById(id);
        List<Comment> allByTopic = commentService.findAllByTopic(oneById);
        if (allByTopic.isEmpty()) {
            modelMap.addAttribute("msg", "there are no comment in this topic, say anything");
        } else {
            modelMap.addAttribute("comments", allByTopic);
        }
        modelMap.addAttribute("singleTopic", oneById);
        modelMap.addAttribute("newComment", new Comment());
        log.info("Opened Single Topic Page, Topic Name {} and requested user {} {} id = {} ",
                oneById.getContent(), currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId());
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
        log.info("Added Comment in topic {} by user {} {} id = {}, Comment -> {}",
                topicService.findOneById(topicId).getContent(), currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId(), comment);
        return "redirect:/singleTopic?id=" + topicId;
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("id") int id, @RequestParam("topicId") int topicId, @AuthenticationPrincipal CurrentUser currentUser) {
        commentService.delete(id, currentUser.getEmployee());
        log.info("Comment with id =  {} was deleted by User {} {} id = {} ",
                id, currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId());
        return "redirect:/singleTopic?id=" + topicId;
    }

}

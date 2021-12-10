package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.GmailService;
import com.example.companyemployeespring.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final MessageService messageService;
    private final GmailService emailService;

    @GetMapping("/employees")
    public String getEmployees(
            @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "size", defaultValue = "20") int size,
            ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<Employee> all = employeeService.findAll(pageable);
        modelMap.addAttribute("employees", all);
        log.info("User with email {} tried to open /employees page ", currentUser.getEmployee().getEmail());
        return "employees";
    }

    @GetMapping("/admin/addEmployee")
    public String redirectToAddEmployee(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getEmployee().getPosition().equals(Position.ADMINISTRATOR)) {
            modelMap.addAttribute("employee", new Employee());
            modelMap.addAttribute("companies", companyService.findAll());
            return "addEmployee";
        }
        log.info("Logged user with email {} tried to open /admin/employee where he has no access",
                currentUser.getEmployee().getEmail());
        return "redirect:/";
    }

    @PostMapping("/admin/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee, @AuthenticationPrincipal CurrentUser currentUser, Locale locale) {
        if (currentUser.getEmployee().getPosition().equals(Position.ADMINISTRATOR)) {
            boolean saved = employeeService.save(employee, locale);
            if (saved) {
                log.info("Added new Employee with email {} at {} by Administrator with id {}",
                        employee.getEmail(), new Date(), currentUser.getEmployee().getId());
            } else {
                log.info("Attempt to create Employee failed cause of bad credentials");
            }
            return "redirect:/admin";
        }
        log.info("Attempt to create employee, User with name {} and email {} ",
                currentUser.getEmployee().getName(), currentUser.getEmployee().getEmail());
        return "redirect:/";
    }

    @GetMapping("/admin/deleteEmployee")
    public String deleteEmployeeById(@RequestParam("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getEmployee().getPosition().equals(Position.ADMINISTRATOR)) {
            employeeService.delete(id);
            log.info("Employee with id {} and Email {} has successfully delete by Administrator {}",
                    currentUser.getEmployee().getId(), currentUser.getEmployee().getEmail(), currentUser.getEmployee().getEmail());
            return "redirect:/employees";
        }
        log.info("Attempt to delete employee, User with name {} and email {} ",
                currentUser.getEmployee().getName(), currentUser.getEmployee().getEmail());
        return "redirect:/";
    }

    @GetMapping("/singleEmployeePage")
    public String singleEmployeePage(ModelMap modelMap, @RequestParam("id") int id) {
        try {
            Optional<Employee> byId = employeeService.findById(id);
            modelMap.addAttribute("employeeById", byId.get());
        } catch (NullPointerException exception) {
            exception.getMessage();
            log.error("Attempt to open /singleEmployeePage where Request Param id is null");
        }
        return "singleEmployee";
    }

    @GetMapping("/loggedEmployee")
    public String getLoggedEmployeePage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Employee> allByCompany_id = new LinkedList<>();
        if (currentUser.getEmployee().getCompany() != null) {
            allByCompany_id = employeeService.findAllByCompany_Id(currentUser.getEmployee().getCompany().getId());
        }
        if (!allByCompany_id.isEmpty()) {
            modelMap.addAttribute("employeesById", allByCompany_id);
            return "loggedEmployee";
        }
        log.info("User {} {} id = {} has successfully opened /loggedEmployee page ",
                currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId());
        return "loggedEmployee";
    }

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam("id") int id, ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        try {
            List<Message> send = messageService.findMessagesByFromIdAndToId(currentUser.getEmployee().getId(), id);
            if (!send.isEmpty()) {
                modelMap.addAttribute("send", send);
            }
        } catch (NullPointerException e) {
            modelMap.addAttribute("msg", e.getMessage());
        }
        modelMap.addAttribute("toEmployee", employeeService.findById(id).get());
        modelMap.addAttribute("emptyMessage", new Message());
        log.info("Opened /sendMessage page with user {} {} id = {} ",
                currentUser.getEmployee().getName(), currentUser.getEmployee().getSurname(), currentUser.getEmployee().getId());
        return "message";
    }

    @PostMapping("/send")
    public String send(@ModelAttribute Message message, @RequestParam("toId") int toId,
                       @AuthenticationPrincipal CurrentUser currentUser) {
        message.setFrom(currentUser.getEmployee());
        message.setTo(employeeService.findById(toId).get());
        message.setTimestamp(new Date(System.currentTimeMillis()).toString());
        messageService.save(message);
        return "redirect:/sendMessage?id=" + toId;
    }

    @GetMapping("/verify")
    public String emailVerify(@RequestParam("email") String email, @RequestParam("token") UUID token) {
        employeeService.verifyEmail(email, token);
        log.info("User with email {} has verified account", email);
        return "redirect:/loginPage";
    }

}

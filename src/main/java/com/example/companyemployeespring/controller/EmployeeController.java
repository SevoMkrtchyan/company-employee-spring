package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.MailService;
import com.example.companyemployeespring.service.MessageService;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final MessageService messageService;
    private final MailService mailService;

    @GetMapping("/employees")
    public String getEmployees(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Employee> employees = employeeService.findAll();
        modelMap.addAttribute("employees", employees);
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
    public String addEmployee(@ModelAttribute Employee employee, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getEmployee().getPosition().equals(Position.ADMINISTRATOR)) {
            employeeService.save(employee);
            mailService.send(employee.getEmail(), "Welcome to Company Employee",
                    "Dear " + employee.getName() + ", You have successfully registered to our web site!");
            log.info("Added new Employee with email {} at {} by Administrator with id {}",
                    employee.getEmail(), new Date(), currentUser.getEmployee().getId());
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
                    employeeService.findById(id).get().getId(), employeeService.findById(id).get().getEmail(), currentUser.getEmployee().getEmail());
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

}

package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.MessageService;
import lombok.RequiredArgsConstructor;
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
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final MessageService messageService;

    @GetMapping("/employees")
    public String getEmployees(ModelMap modelMap) {
        List<Employee> employees = employeeService.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/admin/addEmployee")
    public String redirectToAddEmployee(ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        modelMap.addAttribute("companies", companyService.findAll());
        return "addEmployee";
    }

    @PostMapping("/admin/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/admin";
    }

    @GetMapping("/admin/deleteEmployee")
    public String deleteEmployeeById(@RequestParam("id") int id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/singleEmployeePage")
    public String singleEmployeePage(ModelMap modelMap, @RequestParam("id") int id) {
        try {
            Optional<Employee> byId = employeeService.findById(id);
            modelMap.addAttribute("employeeById", byId.get());
        } catch (NullPointerException exception) {
            exception.getMessage();
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

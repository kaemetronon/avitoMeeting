//package avito.testingAvito.controller;
//
//import etu.course.model.Employee;
//import etu.course.model.Status;
//import etu.course.service.ValidateFields;
//import etu.course.service.dbase.EmployeeDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Map;
//
//@Controller
//@RequestMapping("/upload")
//public class UploadController {
//
//    @Autowired
//    private EmployeeDAO employeeDAO;
//
//    @GetMapping("")
//    public String uploadMainGet() {
//        return "main.upload";
//    }
//
//    @GetMapping("/addToBase")
//    public String addToBaseGet() {
//        return "main.upload.addNew";
//    }
//
//    // вводятся first&lastName. либо Employee загружается в базу,
//    // либо выкидывает сообщение что такой уже есть в базе
//    @PostMapping("/addToBase")
//    public String addToBasePost(@RequestParam @NonNull String emplName,
//                                Map<String, Object> model) {
//
//        Employee employee;
//        String[] firstAndLastName;
//
//        //  проверки
//        firstAndLastName = ValidateFields.checkStringFor2Words(emplName);
//        if (firstAndLastName == null) {
//            model.put("msg", "Uncorrect name");
//            return "main.upload";
//        }
//
//        if (employeeDAO.findByFullName(firstAndLastName[0], firstAndLastName[1]) != null) {
//            model.put("msg", "Employee with the same name already exist.");
//            return "main.upload";
//        }
//        employee = new Employee();
//
//        employee.setFirstName(firstAndLastName[0]);
//        employee.setLastName(firstAndLastName[1]);
//        employee.setStatus(Status.NEW);
//
//        employeeDAO.save(employee);
//
//        model.put("msg", "Employee " + firstAndLastName[0] + " " + firstAndLastName[1] + " was added");
//        return "main.upload";
//    }
//
//    @GetMapping("/changeInfo")
//    public String changeInfo(Map<String, Object> model) {
//
//        Iterable<Employee> employees = employeeDAO.findAll();
//        if (employees == null) {
//            model.put("notExists", "Employee's list is empty");
//            return "main.upload.changeInfo";
//        }
//
//        model.put("exists", employees);
//        model.put("present", "");
//        return "main.upload.changeInfo";
//    }
//}

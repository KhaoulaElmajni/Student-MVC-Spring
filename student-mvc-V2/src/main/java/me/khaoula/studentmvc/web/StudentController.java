package me.khaoula.studentmvc.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.khaoula.studentmvc.entities.Student;
import me.khaoula.studentmvc.repositories.StudentRepository;
import me.khaoula.studentmvc.security.entities.AppRole;
import me.khaoula.studentmvc.security.entities.AppUser;
import me.khaoula.studentmvc.security.repositories.AppRoleRepository;
import me.khaoula.studentmvc.security.repositories.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Data
@AllArgsConstructor
public class StudentController {
    private StudentRepository studentRepository;
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;



    @GetMapping(path = "/user/index")
    public String students(Model model,
                           //param d'url
                           @RequestParam(name="page", defaultValue = "0") int page,
                           @RequestParam(name="size", defaultValue = "5") int size,
                           @RequestParam(name="keyword", defaultValue = "") String keyword){
        Page<Student> studentPage = studentRepository.findByNomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listStudents",studentPage.getContent());
        model.addAttribute("pages",new int[studentPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "students";
    }

    //1
   @GetMapping("/admin/delete")
    public String delete(Long id, String keyword, int page){
        studentRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    //2
    /*@DeleteMapping("/admin/delete/{id}")
    public String  delete(@PathVariable Long id,String keyword, int page) {
        studentRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }*/

    //3
    /*@RequestMapping(value="/admin/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable Long id,String keyword, int page) {
        studentRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }*/
    /*@DeleteMapping("/admin/delete/{id}")
    public void   delete(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }*/

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/user/students")
    @ResponseBody
    public List<Student> listStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/admin/formStudents")
    public String formStudents(Model model){
        model.addAttribute("student",new Student());
        return "formStudents";
    }

    @GetMapping("/user/profile")
    public String profile(Model model, String username){
        AppUser appUser = appUserRepository.findByUsername(username);
        /*List<AppRole> roles = new ArrayList<>();
        for (AppRole role: appUser.getAppRoles()) {
            roles.add(role);
        }
        model.addAttribute("roles",roles);*/
        model.addAttribute("user",appUser);
        return "profile";
    }

    @PostMapping("/admin/save")
    //@Valid ==> je dis à spring mvc une fois tu fais l'ajout d'un etudiant au BDD tu fais la validation
    //si jamais il y a des erreurs tu les stockes dans BindingResult
    public String save(Model model, @Valid Student student, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "")String keyword){
        if (bindingResult.hasErrors())
            return "formStudents";
        studentRepository.save(student);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editStudent")
    public String editStudent(Model model,Long id,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0") int page){
        Student student = studentRepository.findById(id).orElse(null);
        if (student==null) throw new RuntimeException("Student introuvable!!!");
        model.addAttribute("student",student);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editStudent";
    }

    @GetMapping("/user/listStudent")
    public String listStudent(Model model, Long id,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0") int page){
        Student student = studentRepository.findById(id).get();
        model.addAttribute("student",student);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "listStudent";
    }

    @GetMapping("/auth")
    public String login(){
        return "login";
    }
}

package com.example.springboot_security403v2;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        return "index";
    }

    @GetMapping("/addteam")
    public String addTeam(Model model) {
        model.addAttribute("team", new Team());
        return "addteam";
    }

    @PostMapping("/processteam")
    public String processTeam(@Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addteam";                            // compare to redirect ***************
        }
        model.addAttribute("team", team);
        teamRepository.save(team);
        return "teamdetails";
    }

    @GetMapping("/addplayer")
    public String addPlayer(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("teams", teamRepository.findAll());
        return "addplayer";
    }
    @PostMapping("/processplayer")
    public String processPlayer(@Valid Player player, BindingResult result, Model model, @RequestParam(name="file") MultipartFile file) {
        if(result.hasErrors()) {
            model.addAttribute("player", player);             // what was I supposed to do here? ****************
            return "addplayer";
        }
        if (file.isEmpty()) {
            if (player.getPic() == null || player.getPic().isEmpty()) {
                player.setPic("");                     // Might fail if return is null
            }

        }else {
            try {
                Map uploadResult= cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourceType", "auto"));
                player.setPic(uploadResult.get("url").toString());

            }catch (IOException e) {
                e.printStackTrace();
                return "todoform";
            }
        }
        playerRepository.save(player);
        model.addAttribute("player", player);

        return "playerdetails";
    }

    @RequestMapping("/updateteam/{id}")
    public String updateTeam(@PathVariable("id") long id, Model model) {
        model.addAttribute("team", teamRepository.findById(id).get());
        return "addteam";
    }
    @RequestMapping("/deleteteam/{id}")
    public String deleteTeam(@PathVariable("id") long id) {
        teamRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/teamdetails/{id}")
    public String viewTeam(@PathVariable("id") long id, Model model) {
        model.addAttribute("team", teamRepository.findById(id).get());
        return "teamdetails";
    }

    @RequestMapping("/playerdetails/{id}")
    public String viewPlayer(@PathVariable("id") long id, Model model) {
        model.addAttribute("player", playerRepository.findById(id).get());
        return "playerdetails";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/processregister")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.clearPassword();
            model.addAttribute("user", user);
            return "register";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("message", "New user account created");
            user.setEnabled(true);
            userRepository.save(user);

            Role role = new Role(user.getUsername(), "ROLE_USER");
            roleRepository.save(role);
        }
        return "redirect:/login";
    }


    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";

    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }
}

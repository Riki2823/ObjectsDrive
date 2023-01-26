package villanueva.ricardo.Objects.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import villanueva.ricardo.Objects.Model.User;
import villanueva.ricardo.Objects.Service.MyService;

import java.math.BigInteger;
import java.security.MessageDigest;

@Controller
public class AppController {
    @Autowired
    MyService service;

    @GetMapping("/")
    public String home(HttpSession session){
        if (session.getAttribute("user") != null){
            return "redirect:/objects";
        }
        return "home";
    }
    //------------------------------------------------------------------------------

    @GetMapping("/settings")
    public String setting(HttpSession session, Model m){
        User u = service.getUser((String) session.getAttribute("user"));
        m.addAttribute("nickName", u.getUsername());
        return "settings";
    }
    //------------------------------------------------------------------------------
    @GetMapping("/login")
    public String loginget(HttpSession session){
        if (session.getAttribute("user") != null){
            return "redirect:/objects";
        }

        return "login";
    }

    @PostMapping("/login")
    public String addUser(String user, String clientPasswd, HttpSession session,Model m){
        if (!service.userExist(user)){
            m.addAttribute("message", "El usuario introducido no existe");
            return "login";
        }
        String clientHased = getSHA256(clientPasswd);
        String serverPasswd = service.getPasswdByUser(user);
        if (clientHased.equals(serverPasswd)){
            session.setAttribute("user", user);
            return "redirect:/objects";
        }else {
            m.addAttribute("message", "La contraseña introducida no és correcta");
            return "login";
        }
    }

    //------------------------------------------------------------------------------
    @GetMapping("/objects")
    public String objects(HttpSession session, Model m){
        User u = service.getUser((String) session.getAttribute("user"));
        m.addAttribute("userName", u.getRealname());
        return "objects";
    }

    //------------------------------------------------------------------------------
    @GetMapping("/signup")
    public String signupget(){return "signup";}

    @PostMapping("/signup")
    public String signuppost(String user, String passwd1, String passwd2, String realname, Model m){
        if (service.nicknameExists(user)){
            m.addAttribute("message", "El nombre de usuario introducido ya existe!");
            return "signup";
        }

        String passwd = getSHA256(passwd1);

        service.addUser(user, passwd, realname);
        m.addAttribute("message", "La creacion de una cuenta ha sido realizada correctamente");
        return "signup";
    }

    //------------------------------------------------------------
    private boolean checkPassd(String passwd2, String passwd1) {
        return passwd1.equals(passwd2);
    }

    public static String getSHA256(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}

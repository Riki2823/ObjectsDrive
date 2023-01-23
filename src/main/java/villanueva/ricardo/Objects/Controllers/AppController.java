package villanueva.ricardo.Objects.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import villanueva.ricardo.Objects.Service.MyService;

@Controller
public class AppController {
    @Autowired
    MyService service;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String loginget(){
        return "login";
    }

    @PostMapping("/login")
    public String addUser(String user, String clientPasswd){
        String clientHased = getSHA256(clientPasswd);
        String serverPasswd = service.getPasswdByUser(user);
        if (clientHased.equals(serverPasswd)){
            System.out.println(clientHased);
            System.out.println(serverPasswd);
            System.out.println("Son iguales");
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupget(){return "signup";}

    @PostMapping("/signup")
    public String signuppost(String user, String passwd1, String passwd2, String realname, Model m){
        if (!checkPassd(passwd2, passwd1)){
            m.addAttribute("message", "Las contraseñas introducidas no son iguales");
            return "signup";
        }
        if (service.nicknameExists(user)){
            m.addAttribute("message", "El nombre de usuario introducido ya existe!");
            return "signup";
        }

        String passwd = getSHA256(passwd1);

        service.addUser(user, passwd, realname);
        m.addAttribute("message", "La creacion de una cuenta ha sido realizada correctamente");
        return "signup";
    }

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

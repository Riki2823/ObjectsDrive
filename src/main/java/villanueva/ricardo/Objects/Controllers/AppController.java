package villanueva.ricardo.Objects.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import villanueva.ricardo.Objects.Model.Bucket;
import villanueva.ricardo.Objects.Model.User;
import villanueva.ricardo.Objects.Service.MyService;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/")
    public String logout(HttpSession session){
        session.invalidate();
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
        String clientHased = service.getSHA256(clientPasswd);
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
        List<Bucket> buckets = service.getBucketsByUser(u.getUsername());
        m.addAttribute("buckets", buckets);
        return "objects";
    }

    @PostMapping("/objects")
    public String postObjects(HttpSession session, String bucketName, Model m) {
        String userName = (String) session.getAttribute("user");
        service.addBucket(bucketName, userName);
        return "redirect:objects";
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

        String passwd = service.getSHA256(passwd1);

        service.addUser(user, passwd, realname);
        m.addAttribute("message", "La creacion de una cuenta ha sido realizada correctamente");
        return "signup";
    }
    //------------------------------------------------------------------------------

    @GetMapping("/objects/{bucket}")
    public String seebucket(@PathVariable String bucket, Model m){
        m.addAttribute("bname", bucket);
        return "bucketCont";
    }

    @PostMapping("/objects/{bucket}")
    public String addObject(HttpSession session, @PathVariable String bucket, MultipartFile file){
        try {
            byte[] bytesFile = file.getBytes();
            String nickname = (String) session.getAttribute("user");
            String name = file.getOriginalFilename();
            if (service.objectExists(name)){
                System.out.println("Ya existe");
            }else {
                service.uploadFileFirstTime(bytesFile, bucket, name, nickname);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "bucketCont";
    }

    //------------------------------------------------------------


}

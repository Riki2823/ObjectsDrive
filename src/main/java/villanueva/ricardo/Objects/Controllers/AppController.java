package villanueva.ricardo.Objects.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import villanueva.ricardo.Objects.Model.*;
import villanueva.ricardo.Objects.Model.Object;
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

    @PostMapping("/settings")
    public String changeUserSettings(HttpSession session, Model m, String newName, String actualpasswd, String newPasswd1, String validationDelete){
        String nickname = (String) session.getAttribute("user");

        if (newName != null){
            if (service.getSHA256(actualpasswd).equals(service.getPasswdByUser(nickname))){
                service.uploadNameUser(nickname, newName);
                m.addAttribute("message", "El nombre Real ha sido cambiado correctamente");
            }
            else {
                m.addAttribute("message", "La contraseña introducida no es correcta");
                return "settings";
            }
        }

        if (newPasswd1 != null){
            if (service.getSHA256(actualpasswd).equals(service.getPasswdByUser(nickname))){
                service.uploadPasswd(nickname, service.getSHA256(newPasswd1));
                m.addAttribute("message", "La contraseña ha sido cambiada correctamente");

            }else {
                m.addAttribute("message", "La contraseña introducida no es correcta");
                return "settings";
            }
        }

        if (validationDelete != null) {
            session.invalidate();
            service.deleteUser(nickname);
            return "redirect:/";
        }

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
            return "redirect:objects";
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
    public String postObjects(HttpSession session, String bucketName, Model m, HttpServletRequest request) {
        User u = service.getUser((String) session.getAttribute("user"));
        String userName = u.getUsername();
        bucketName = bucketName.toLowerCase();
        if (service.bucketExists(bucketName)){
            m.addAttribute("message", "El nombre del bucket ya existe, debes introducir un nombre diferente");
            m.addAttribute("userName", u.getRealname());
            m.addAttribute("buckets", service.getBucketsByUser(userName));
            return "objects";


        }
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
    public String seebucket(HttpSession session,  @PathVariable String bucket, Model m, HttpServletRequest request){
        String bucketOwner = service.getOwnerBucket(bucket);
        String nickname = (String) session.getAttribute("user");
        if (!bucketOwner.equals(nickname)){
            m.addAttribute("message", "Ese bucket no es de tu propiedad");
            m.addAttribute("userName", nickname);
            m.addAttribute("buckets", service.getBucketsByUser(nickname));
            return "objects";
        }
        List<Object> objects = service.getAllBucketObjects(bucket);
        m.addAttribute("objects", objects);
        m.addAttribute("bname", bucket);
        return "bucketCont";
    }

    @PostMapping("/objects/{bucket}")
    public String addObject(HttpSession session, String dirrectory ,@PathVariable String bucket, MultipartFile file, Model m, HttpServletRequest request){
        if (file != null && dirrectory == null){
            try {
                byte[] bytesFile = file.getBytes();
                String nickname = (String) session.getAttribute("user");
                User user = service.getUser(nickname);
                String name = file.getOriginalFilename();

                String uri = request.getRequestURI();

                if (service.objectExists(name, request.getRequestURI())){
                    service.checkVersion(bytesFile, user, uri, name);
                }else {
                    service.uploadFileFirstTime(bytesFile, bucket, name, nickname, uri, user);
                }
                List<Object> objects = service.getAllBucketObjects(bucket);
                m.addAttribute("objects", objects);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            m.addAttribute("bname", bucket);
            return "bucketCont";
        } else if( dirrectory != null && file == null){
            m.addAttribute("bname", bucket);
            System.out.println("Crear directorio");
            return "bucketCont";
        }
        m.addAttribute("message", "No puedes subir un archivo y crear un directorio al mismo tiempo ");
        return "bucketCont";
    }


    //------------------------------------------------------------

    @PostMapping("/deletebucket/{bucket}")
    public String deleteBucket(HttpSession session, @PathVariable String bucket, Model m){
        String bucketOwner = service.getOwnerBucket(bucket);
        String nickname = (String) session.getAttribute("user");
        if (!bucketOwner.equals(nickname)){
            m.addAttribute("message", "Ese bucket no es de tu propiedad");
            m.addAttribute("userName", nickname);
            m.addAttribute("buckets", service.getBucketsByUser(nickname));
            return "objects";
        }else {
            service.deleteBucket(bucket);
            return "redirect:/objects";
        }
    }

    @GetMapping("/objects/{bucket}/{object}")
    public String getVersions(HttpSession session, @PathVariable String bucket, @PathVariable String object, Model m){
        String bucketOwner = service.getOwnerBucket(bucket);
        String nickname = (String) session.getAttribute("user");
        if (!bucketOwner.equals(nickname)){
            m.addAttribute("message", "Ese bucket no es de tu propiedad");
            m.addAttribute("userName", nickname);
            m.addAttribute("buckets", service.getBucketsByUser(nickname));
            return "objects";
        }else {
            Object object1 = service.getObject("/" + bucket + "/" + object);
            List<Version> versions = service.getAllVersions(object1.getId());
            m.addAttribute("oname", object1.getName());
            m.addAttribute("versions", versions);
            return "objectView";
        }
    }

    @GetMapping("/download/{objid}/{fid}")
    public ResponseEntity<byte[]> download (HttpSession session, @PathVariable String objid, @PathVariable String fid, Model m ){

            File file = service.getFileById(fid);
            Object object = service.getObjectById(objid);

            byte[] content = file.getContent();
            String nameFile = object.getName();

            HttpHeaders header = new HttpHeaders();

            header.setContentType(MediaType.valueOf("application/octet-stream"));
            header.setContentLength(content.length);
            header.set("content-disposition", "attachment;filename=" + nameFile);

            return new ResponseEntity<>(content, header, HttpStatus.OK);

    }

}

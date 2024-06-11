package org.tfg.spring.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.tfg.spring.tfg.domain.Usuario;
import org.tfg.spring.tfg.exception.DangerException;
import org.tfg.spring.tfg.helper.PRG;
import org.tfg.spring.tfg.service.MailService;
import org.tfg.spring.tfg.service.MarcaService;
import org.tfg.spring.tfg.service.ModeloService;
import org.tfg.spring.tfg.service.UsuarioService;
import org.tfg.spring.tfg.service.ZapatillaService;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ZapatillaService zapatillaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public String home(ModelMap m) {
        m.put("view", "home/home");
        marcaService.init();
        modeloService.init();
        zapatillaService.init();
        
        if (usuarioService.findById(1L) == null) {

            usuarioService.save("admin", null, null, "admin");
            usuarioService.setAdmin("admin");
        }
        
        return "_t/frame";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, ModelMap m) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !Boolean.TRUE.equals(usuario.getAdmin())) {
            return "redirect:/login";
        }
        m.put("view", "home/admin");
        return "_t/frame";
    }

    @GetMapping("/pricing")
    public String pricing(ModelMap m) {
        m.put("view", "home/pricing");
        return "_t/frame";
    }

    @GetMapping("/features")
    public String features(ModelMap m) {
        m.put("view", "home/features");
        return "_t/frame";
    }

    @GetMapping("/faqs")
    public String faqs(ModelMap m) {
        m.put("view", "home/faqs");
        return "_t/frame";
    }

    @GetMapping("/about")
    public String about(ModelMap m) {
        m.put("view", "home/about");
        return "_t/frame";
    }

    @GetMapping("/news")
    public String news(ModelMap m) {
        m.put("view", "home/news");
        return "_t/frame";
    }

    @GetMapping("/catalogue")
    public String catalogue(ModelMap m) {
        String palabraClave = ""; // Aquí puedes proporcionar una palabra clave válida para la búsqueda de zapatillas
        m.put("zapatillas", zapatillaService.findAll(palabraClave));
        m.put("view", "home/catalogue");
        return "_t/frame";
    }

    @GetMapping("/vipzone")
    public String vipzone(ModelMap m) {
        m.put("view", "home/vipzone");
        return "_t/frame";
    }

    @GetMapping("/pay")
    public String pay(ModelMap m) {
        m.put("view", "home/pay");
        return "_t/frame";
    }

    @GetMapping("/shoes")
    public String shoes(ModelMap m) {
        m.put("view", "home/shoes");
        return "_t/frame";
    }

    @GetMapping("/info")
    public String info(HttpSession s, ModelMap m) {

        String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
                : "Pulsa para volver a home";
        String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
        String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

        s.removeAttribute("_mensaje");
        s.removeAttribute("_severity");
        s.removeAttribute("_link");

        m.put("mensaje", mensaje);
        m.put("severity", severity);
        m.put("link", link);

        m.put("view", "/_t/info");
        return "/_t/frame";
    }

    @GetMapping("/signup")
    public String signup(ModelMap m) {
        m.put("view", "home/signup");
        return "_t/frame";
    }

    @PostMapping("/signup")
    public String signupPost(
            @RequestParam("nombre") String nombre,
            @RequestParam("dni") String dni,
            @RequestParam("mail") String mail,
            @RequestParam("contraseña") String contraseña,
            HttpSession s,
            ModelMap m) {
        try {
            Usuario usuario = usuarioService.save(nombre, dni, mail, contraseña);
            mailService.sendActivationEmail(usuario);
        } catch (Exception e) {
            try {
                PRG.error("El usuario " + nombre + " ya existe", "/usuario/c");
            } catch (DangerException e1) {
                
                e1.printStackTrace();
            }
        }
        m.put("view", "home/home");
        return "_t/frame";
    }

    @GetMapping("/login")
    public String login(ModelMap m) {
        m.put("view", "home/login");
        return "_t/frame";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("nombre") String nombre,
            @RequestParam("contraseña") String contraseña,
            HttpSession s,
            ModelMap m) throws DangerException {
        try {
            Usuario usuario = usuarioService.login(nombre, contraseña);
            s.setAttribute("usuario", usuario);
        } catch (Exception e) {
            PRG.error("Usuario o contraseña incorrectos");
        }
        m.put("view", "home/home");
        return "_t/frame";
    }

    @GetMapping("/logout")
    public String logout(HttpSession s) {
        s.invalidate();
        return "redirect:/";
    }


}

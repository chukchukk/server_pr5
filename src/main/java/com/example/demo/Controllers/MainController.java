package com.example.demo.Controllers;

import com.example.demo.Model.PDFFile;
import com.example.demo.Model.User;
import com.example.demo.Repositories.PDFFileRepository;
import com.example.demo.Roles.Role;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Контроллер, отвечающий за весь
 * функционал личного кабинета
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Controller
public class MainController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PDFFileRepository pdfFileRepository;

    /**
     * Возврат домашней страницы
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        if (user != null) {
            if (user.getRoles().contains(Role.USER))
                model.addAttribute("userRole", "user");
        }
        return "home";
    }

    /**
     *Возврат страницы личного кабинета
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @param model модель страницы main
     * @return страницу личного кабинета
     */
    @GetMapping("/main")
    public String mainPage(HttpServletRequest request,
                           @AuthenticationPrincipal User user,
                           Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("background")) {
                    model.addAttribute("background", c.getValue());
                }
            }
        }
        model.addAttribute("files", pdfFileRepository.findAll());
        return authorizationService.getMainPage(user, model);
    }

    @PostMapping("/sendPDF")
    public String sendPDF(@RequestParam MultipartFile[] pdf) {
        for (MultipartFile file : pdf) {
            try {
                PDFFile pdfFile = new PDFFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                pdfFileRepository.save(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/main";
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
        PDFFile pdfFile = pdfFileRepository.findPDFFileById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(pdfFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + pdfFile.getName() + "\"")
                .body(new ByteArrayResource(pdfFile.getData()));
    }

    @GetMapping("/setContentParams")
    public String setContentParams(@RequestParam String background,
                                   HttpServletResponse response) {
        Cookie backgroundCookie = new Cookie("background", background);
        backgroundCookie.setMaxAge(365*24*60*60);
        response.addCookie(backgroundCookie);
        return "redirect:/main";
    }

    /**
     *Возврат страницы для смены пароля
     *
     * @return страницу для смены личного кабинета
     */
    @GetMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "changePage";
    }

    /**
     * Процесс смены пароля с необходимыми проверками
     *
     * @param oldPassword a {@link java.lang.String} object.
     * @param newPassword a {@link java.lang.String} object.
     * @param model a {@link org.springframework.ui.Model} object.
     * @return a {@link java.lang.String} object.
     */
    @PostMapping("/changePasswordAction")
    public String changePasswordAction(@ModelAttribute("old_password") String oldPassword,
                                       @ModelAttribute("password") String newPassword,
                                       Model model) {
        return authorizationService.changingPassword(oldPassword, newPassword, model);
    }
}

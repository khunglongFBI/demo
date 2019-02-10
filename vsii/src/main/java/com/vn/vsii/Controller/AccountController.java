package com.vn.vsii.Controller;
import com.vn.vsii.Service.AccountService;
import com.vn.vsii.model.Account;
import com.vn.vsii.model.Role;
import com.vn.vsii.reponsitory.RoleReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private RoleReponsitory roleRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @ModelAttribute("roles")
    public Iterable<Role> roles() {
        return roleRepository.findAll();
    }


    @GetMapping("/admin/account/create-account")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/admin/account/create");
        modelAndView.addObject("account", new Account());
        return modelAndView;
    }

    @PostMapping("/create-account")
    public ModelAndView saveUser(@Validated @ModelAttribute("account") Account account, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/admin/account/create");
        } else {
            if (accountService.findByUserName(account.getUserName()) == null) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                accountService.setupRole(account.getRole(), account);
                accountService.save(account);
                ModelAndView modelAndView = new ModelAndView("/admin/account/create");
                modelAndView.addObject("account", new Account());
                modelAndView.addObject("message", "New account created successfully");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("/admin/account/create");
                modelAndView.addObject("account", new Account());
                modelAndView.addObject("message", "Tên bị trùng");
                return modelAndView;
            }
        }
    }


    @GetMapping("/admin")
        public ModelAndView listUser(@RequestParam("s") Optional<String> s,
                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(name = "size", required = false, defaultValue = "6") Integer size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Account> accounts;
            if (s.isPresent()) {
                accounts = accountService.findAllByStudentName(s.get(), pageable);
            } else {
                accounts = accountService.findAll(pageable);
            }
            ModelAndView modelAndView = new ModelAndView("admin/account/list");
            modelAndView.addObject("accounts", accounts);
            return modelAndView;
        }

        @GetMapping("/edit-account/{id}")
        public ModelAndView showEditForm(@PathVariable Long id) {
            Account account = accountService.findById(id);
            if (account != null) {
                ModelAndView modelAndView = new ModelAndView("/admin/account/edit");
                modelAndView.addObject("account", account);
                return modelAndView;

            } else {
                return new ModelAndView("/error.404");
            }
        }

        @PostMapping("/edit-account")
        public ModelAndView updateUser(@ModelAttribute("account") Account account) {
            accountService.setupRole(account.getRole(), account);
            accountService.save(account);
            ModelAndView modelAndView = new ModelAndView("/admin/account/edit");
            modelAndView.addObject("account", account);
            modelAndView.addObject("message", "updated successfully");
            return modelAndView;
        }

        @GetMapping("/delete-account/{id}")
        public ModelAndView showDeleteForm(@PathVariable Long id) {
            Account account = accountService.findById(id);
            if (account != null) {
                ModelAndView modelAndView = new ModelAndView("/admin/account/delete");
                modelAndView.addObject("account", account);
                return modelAndView;

            } else {
                return new ModelAndView("admin/account/error.404");
            }
        }

        @PostMapping("/delete-account")
        public String deleteUser(@ModelAttribute("account") Account account) {
            accountService.remove(account.getId());
            return "redirect:/admin";
        }

        @GetMapping("user/change_password")
        public ModelAndView showChangePass() {
            return new ModelAndView("/user/change_password");
        }

        @PostMapping("/change_password")
        public ModelAndView ChangePass(Principal principal,
                                       @RequestParam(name = "newpassword") String newpassword,
                                       @RequestParam(name = "newpassword1") String newpassword1) {
            ModelAndView modelAndView = new ModelAndView("user/change_password");
            Account account = accountService.findByUserName(principal.getName());
            if (newpassword.equals(newpassword1)) {
                account.setPassword(passwordEncoder.encode(newpassword));
                accountService.save(account);
                modelAndView.addObject("message", "updated successfully");
                return modelAndView;
            } else {
                modelAndView.addObject("message", "mật khẩu không trùng khớp");
                return modelAndView;
            }
        }

        @GetMapping("/user")
        public ModelAndView ShowInfo(Principal principal) {
            ModelAndView modelAndView = new ModelAndView("user/view");
            Account account = accountService.findByUserName(principal.getName());
            modelAndView.addObject("account", account);
            return modelAndView;
        }

        @GetMapping("/lock-open/{id}")
        public String closeOpen(@PathVariable Long id) {
            Account account = accountService.findById(id);
            if (account != null) {
                account.setLocked(!account.getIsLocked());
                accountService.save(account);
                return "redirect:/admin";

            } else {
                return "admin/account/error.404";
            }
        }
    @GetMapping("/account/search")
    public String search(@RequestParam("q") String q, Model model) {
        if (q.equals("")) {
            return "redirect:/admin";
        }
        model.addAttribute("account", accountService.findByUserName(q));
        return "/admin/account/list";
    }

}

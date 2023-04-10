//package ru.kata.spring.boot_security.demo.ErorrsValidator;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//
//
//@Component
//public class UserValidator implements Validator {
//    private final UserService userDetailsService;
//    @Autowired
//    public UserValidator(UserService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return User.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        User user = (User) target;
//        try {
//            userDetailsService.findByUsername(user.getUsername());
//        } catch (UsernameNotFoundException ignored) {
//            return;
//        }
//        errors.rejectValue("username", "", "Пользователь с таким именем--существует");
//    }
//}

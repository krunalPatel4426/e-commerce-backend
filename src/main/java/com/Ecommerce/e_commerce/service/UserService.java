package com.Ecommerce.e_commerce.service;

import com.Ecommerce.e_commerce.DTO.*;
import com.Ecommerce.e_commerce.model.Order;
import com.Ecommerce.e_commerce.model.User;
import com.Ecommerce.e_commerce.model.VerificationToken;
import com.Ecommerce.e_commerce.repo.UserRepo;
import com.Ecommerce.e_commerce.repo.VerificationTokenRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private VerificationTokenRepo tokenRepo;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager manager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<Object> register(User user){
       try{
           user.setEmailVerified(false);
           user.setPassword(encoder.encode(user.getPassword()));
           repo.save(user);

           String token = tokenService.createVerificationToken(user);

           String verificationLink = "http://localhost:5500/verify.html?token=" + token;

           emailService.sendVerificationEmail(user.getEmail(), verificationLink);

           return ResponseEntity.status(HttpStatus.CREATED).body(new RegistrationDTO("User Registred successfully.", "CREATED"));
       }catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegistrationDTO("User Already Exists.", "BAD_REQUEST"));
       }
    }

    public ResponseEntity<Object> verifyToken(String token){
        VerificationToken verificationToken = tokenRepo.findByToken(token);
        if(verificationToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RegistrationDTO("Invalid Token.", "UNAUTHORIZED"));
        }
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RegistrationDTO("Token Expired.", "UNAUTHORIZED"));
        }
        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        repo.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new RegistrationDTO("Email Verified.", "ACCEPTED"));
    }

    public String verify(User user){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }else{
            return "Failed";
        }
    }

    public ResponseEntity<Object> login(LoginRequest loginRequest, HttpServletResponse res) {
        try{
            User DBUser = repo.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
            if(!DBUser.equals(null)){
                String DBPassword = DBUser.getPassword();
                if(encoder.matches(loginRequest.getPassword(), DBPassword)){
                    System.out.println("Cookie");
                    String jwtToken = jwtService.generateToken(DBUser.getUsername());
                    Cookie jwtCookie = new Cookie("auth_token", jwtToken);
                    jwtCookie.setHttpOnly(false); // Prevent JavaScript access
                    jwtCookie.setSecure(true);  // Set to true in production (HTTPS required)
                    jwtCookie.setPath("/");      // Cookie available across all routes
                    jwtCookie.setAttribute("SameSite", "None");
                    jwtCookie.setMaxAge(24 * 60 * 60); // 1-day expiry
                    res.addCookie(jwtCookie);

                    UserDTO user = new UserDTO(DBUser.getId(), DBUser.getUsername(),
                            DBUser.getEmail(), DBUser.getName(), DBUser.getPhone(), DBUser.getAddress());
                    return ResponseEntity.status(HttpStatus.OK).body(new LoginDTO("Login Successfully.", jwtToken, user, "OK"));
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginDTO("Login Failed. Invalid credentials",null, null, "BAD_REQUEST"));
                }
            }else{
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new RegistrationDTO("User Not Found.", "NOT_FOUND"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new RegistrationDTO("Server Error", "INTERNAL_SERVER_ERROR"));
        }
    }

    public ResponseEntity<Object> getUserDetials(UUID userId) {
        try{
            if(userId == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("Body not found.", 404));
            }

            Optional<User> user = repo.findById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Internal server error.", 500));
        }
    }

    public ResponseEntity<Object> changePassword(UUID userId, ChangePasswordReqDTO changePasswordReqDTO) {
        try{
            Optional<User> user = repo.findById(userId);
            if(encoder.matches(changePasswordReqDTO.getCurrentPassword(), user.get().getPassword())){
                String newPassword = encoder.encode(changePasswordReqDTO.getNewPassword());
                repo.updatePassword(userId, newPassword);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO("Password changed successfully.", 200));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ErrorDTO("Old password not matches", 406));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error while changeing password", 500));
        }
    }

    public ResponseEntity<Object> getOrderDetails(UUID userId) {
        try{
            return null;
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error while fetching data", 500));
        }
    }

    public ResponseEntity<Object> updateAddress(UUID userId, UpdateAddressReqDTO updateAddressReqDTO) {
        try{
            repo.updateAddress(userId, updateAddressReqDTO.getAddress());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Address Updated Successfully.", 200));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error", 500));
        }
    }
}
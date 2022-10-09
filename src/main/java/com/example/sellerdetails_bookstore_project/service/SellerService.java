package com.example.sellerdetails_bookstore_project.service;

import com.example.sellerdetails_bookstore_project.dto.ChangePasswordDTO;
import com.example.sellerdetails_bookstore_project.dto.ForgotPasswordDTO;
import com.example.sellerdetails_bookstore_project.dto.LoginDTO;
import com.example.sellerdetails_bookstore_project.dto.SellerDTO;
import com.example.sellerdetails_bookstore_project.exception.SellerException;
import com.example.sellerdetails_bookstore_project.model.SellerDetails;
import com.example.sellerdetails_bookstore_project.repository.SellerRepository;
import com.example.sellerdetails_bookstore_project.utility.EmailSenderService;
import com.example.sellerdetails_bookstore_project.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService implements ISellerService{
    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private EmailSenderService emailSender;
    @Autowired
    private SellerRepository sellerRepository;

    //Register Seller
    @Override
    public String registerSeller(SellerDTO sellerDTO) throws SellerException {
        LocalDateTime createdTime = LocalDateTime.now();
        boolean verify = true;
        SellerDetails sellerDetails = new SellerDetails(sellerDTO.getBusinessName(), sellerDTO.getSellerName(), sellerDTO.getProfilePic(), sellerDTO.getGstn(), sellerDTO.getSellerWebsite(), sellerDTO.getEmailAddress(), sellerDTO.getUserName(), sellerDTO.getPassword(), sellerDTO.getContactNumber(), verify, createdTime, null);
        sellerRepository.save(sellerDetails);
        String token = tokenUtility.createToken(sellerDetails.getId());
        //sending email
        emailSender.sendEmail(sellerDetails.getEmailAddress(), "Data Added!!!", "Your Account is registered! Please Click on the below link for the details."+"\n"+"http://localhost:7070/seller/SellerData/"+token);
        return token;
    }
    //Get seller data by Token
    @Override
    public SellerDetails getSellerDataByToken(String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        if(sellerDetails.isPresent()){
            return sellerDetails.get();
        }else
            throw new SellerException("Invalid Token");
    }

    //Get all Sellers list
    @Override
    public List<SellerDetails> getAllSellerData() {
        List<SellerDetails> sellerDetailsList = sellerRepository.findAll();
        if (sellerDetailsList.isEmpty()) {
            throw new SellerException("No Seller Registered yet!!!!");
        } else
            return sellerDetailsList;
    }
    //Get the Seller details by ID:
    @Override
    public SellerDetails getSellerDataById(Long id) {
        SellerDetails userDetails = sellerRepository.findById(id).orElse(null);
        if (userDetails != null) {
            return userDetails;
        } else
            throw new SellerException("ID: " + id + ", does not exist");
    }
    //Get Seller details by email address
    @Override
    public SellerDetails getSellerDataByEmailAddress(String email) {
        SellerDetails userDetails = sellerRepository.findByEmailAddress(email);
        if (userDetails != null) {
            return userDetails;
        } else
            throw new SellerException("Email Address: " + email + ", does not exist");
    }
    //Login check
    @Override
    public String loginUser(LoginDTO loginDTO) {
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(loginDTO.emailAddress));
        if(sellerDetails.isPresent()) {
            if(sellerDetails.get().getPassword().equals(loginDTO.getPassword())) {
                emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Login", "Login Successful!");
                return "Login Successful!!";
            } else
                emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Login", "You have entered Invalid password!");
            throw new SellerException("Login Failed, Wrong Password!!!");
        }else
            throw new SellerException("Login Failed, Entered wrong email or password!!!");
    }
    //Change password
    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(changePasswordDTO.getEmailAddress()));
        String password = changePasswordDTO.getPassword();
        if(sellerDetails.isPresent() && sellerDetails.get().getPassword().equals(changePasswordDTO.getOldPassword())){
            sellerDetails.get().setPassword(password); //changing password
            //Sending Email
            emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Password Change!", "Password Changed Successfully!");
            sellerRepository.save(sellerDetails.get());
            return "Password Changed and email sent!";
        }else
            return "Invalid Email Address or Old Password";
    }
    //Sending email for the forgot password, will receive email of reset password
    @Override
    public String forgotPassword(String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        if(sellerDetails.isEmpty()){
            throw new SellerException("Invalid Email Address");
        }else
            emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Forgot Password", "Please click on the below link to reset password"+"\nhttp://localhost:7070/seller/resetPassword/"+token);
            return "Password link shared to your email address";

//        if(sellerDetails != null){
//            emailSender.sendEmail(sellerDetails.getEmailAddress(), "Forgot Password", "Please click on the below link to reset password"+"\nhttp://localhost:7070/seller/resetPassword/"+token);
//            return "Password link shared to your email address";
//        }else
//            throw new SellerException("Invalid Email Address");
    }
    //reset password
    @Override
    public String resetPassword(ForgotPasswordDTO forgotPasswordDTO, String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        if(sellerDetails.get().getEmailAddress().equals(forgotPasswordDTO.getEmailAddress()) && sellerDetails.isPresent()){
            sellerDetails.get().setPassword(forgotPasswordDTO.getNewPassword());
            sellerRepository.save(sellerDetails.get());
            return "Password Reset successful!";
        }else
            throw new SellerException("Invalid Email Address");
    }
    //Update data by Token
    @Override
    public SellerDetails updateDataByToken(SellerDTO sellerDTO, String email, String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(email));
        if(sellerDetails.isPresent() && sellerDetails.get().getId().equals(sellerId)){
            LocalDateTime updatedTimeStamp = LocalDateTime.now();
            sellerDetails.get().setBusinessName(sellerDTO.getBusinessName());
            sellerDetails.get().setSellerName(sellerDTO.getSellerName());
            sellerDetails.get().setProfilePic(sellerDTO.getProfilePic());
            sellerDetails.get().setGstn(sellerDTO.getGstn());
            sellerDetails.get().setSellerWebsite(sellerDTO.getSellerWebsite());
            sellerDetails.get().setEmailAddress(sellerDTO.getEmailAddress());
            sellerDetails.get().setUserName(sellerDTO.getUserName());
            sellerDetails.get().setPassword(sellerDTO.getPassword());
            sellerDetails.get().setContactNumber(sellerDTO.getContactNumber());
            sellerDetails.get().setUpdatedTimeStamp(updatedTimeStamp);
            //Savin the updated data
            sellerRepository.save(sellerDetails.get());
            //sending email
            emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Data Updated!!!", "Please Click on the below link for the updated details."+"\n"+"http://localhost:7070/seller/SellerData/"+token);
            return sellerDetails.get();
        } else
            throw new SellerException("Invalid Email Address: " + sellerDTO.getEmailAddress());
        }

    @Override
    public Optional<SellerDetails> deleteSellerData(String email, String token) {
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(email));
        Long sellerId = tokenUtility.decodeToken(token);
        if(sellerDetails.isPresent() && sellerDetails.get().getId().equals(sellerId)){
            sellerRepository.deleteById(sellerId);
            //sending email
            emailSender.sendEmail(email, "Data Deleted!!!", "Your Data deleted successfully from the BookStore Seller Application, with the Seller ID: "+sellerId);
        }else {
            throw new SellerException("Invalid email Address");
        }
        return sellerDetails;
        }
}

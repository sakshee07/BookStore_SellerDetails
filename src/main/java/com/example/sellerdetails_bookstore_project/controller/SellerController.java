package com.example.sellerdetails_bookstore_project.controller;

import com.example.sellerdetails_bookstore_project.dto.*;
import com.example.sellerdetails_bookstore_project.model.SellerDetails;
import com.example.sellerdetails_bookstore_project.service.ISellerService;
import com.example.sellerdetails_bookstore_project.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    ISellerService sellerService;
    @Autowired
    TokenUtility tokenUtility;
    //Register Seller
    @PostMapping("/add")
    public ResponseEntity<String> AddSellerDetails(@Valid @RequestBody SellerDTO sellerDTO) {
        String response = sellerService.registerSeller(sellerDTO);
        ResponseDTO respDTO = new ResponseDTO("Data Added Successfully and email sent to the User", response);
        return new ResponseEntity(respDTO, HttpStatus.CREATED);
    }
    //Get Seller Details by token
    @GetMapping("/SellerData/{token}")
    public ResponseEntity<String> getUserDetails(@PathVariable String token) {
        SellerDetails sellerDetails = sellerService.getSellerDataByToken(token);
        Long Userid = tokenUtility.decodeToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully for the ID: " + Userid, sellerDetails);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }
    //Get all seller data
    @GetMapping("/allSeller")
    public ResponseEntity<ResponseDTO> getAllUserDetails() {
        List<SellerDetails> sellerDetailsList = sellerService.getAllSellerData();
        ResponseDTO responseDTO = new ResponseDTO("All Sellers List, total count: " + sellerDetailsList.size(), sellerDetailsList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get User Data by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDTO> getUserDetailsById(@PathVariable Long id) {
        SellerDetails sellerDetails = sellerService.getSellerDataById(id);
        ResponseDTO responseDTO = new ResponseDTO("Seller Details with the ID: " + id, sellerDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get User Data by Email Address
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String email) {
        SellerDetails sellerDetails = sellerService.getSellerDataByEmailAddress(email);
        ResponseDTO responseDTO = new ResponseDTO("Seller Details with the Email Address: " + email, sellerDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Login check
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        String response = sellerService.loginUser(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Status:", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Change password
    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        String response = sellerService.changePassword(changePasswordDTO);
        ResponseDTO responseDTO = new ResponseDTO("Password Status:", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Forgot Password
    @PostMapping("/forgotPassword/{token}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String token) {
        String response = sellerService.forgotPassword(token);
        ResponseDTO responseDTO = new ResponseDTO("Password Link Shared to email", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //reset password to the new password(forgot old password)
    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO, @PathVariable String token) {
        String response = sellerService.resetPassword(forgotPasswordDTO, token);
        ResponseDTO responseDTO = new ResponseDTO("Password Reset", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Updating the User Data using token
    @PutMapping("/update/{email}/{token}")
    public ResponseEntity<ResponseDTO> updateSellerByToken(@PathVariable String token, @PathVariable String email, @Valid @RequestBody SellerDTO sellerDTO) {
        SellerDetails userData = sellerService.updateDataByToken(sellerDTO, email, token);
        ResponseDTO respDTO = new ResponseDTO("Data Update info", userData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //delete the Seller details using token
    @DeleteMapping("/delete/{email}/{token}")
    public ResponseEntity<ResponseDTO> deleteSellerDataByToken(@PathVariable String email, @PathVariable String token) {
        Optional<SellerDetails> deletedData = sellerService.deleteSellerData(email, token);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully with email address: "+email+", and e-mail sent, Below Data is deleted", deletedData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
}

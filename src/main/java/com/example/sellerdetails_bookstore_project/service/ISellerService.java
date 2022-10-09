package com.example.sellerdetails_bookstore_project.service;

import com.example.sellerdetails_bookstore_project.dto.ChangePasswordDTO;
import com.example.sellerdetails_bookstore_project.dto.ForgotPasswordDTO;
import com.example.sellerdetails_bookstore_project.dto.LoginDTO;
import com.example.sellerdetails_bookstore_project.dto.SellerDTO;
import com.example.sellerdetails_bookstore_project.model.SellerDetails;

import java.util.List;
import java.util.Optional;

public interface ISellerService {
    String registerSeller(SellerDTO sellerDTO);
    SellerDetails getSellerDataByToken(String token);

    List<SellerDetails> getAllSellerData();

    SellerDetails getSellerDataById(Long id);

    SellerDetails getSellerDataByEmailAddress(String email);

    String loginUser(LoginDTO loginDTO);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    String forgotPassword(String token);

    String resetPassword(ForgotPasswordDTO forgotPasswordDTO, String token);

    SellerDetails updateDataByToken(SellerDTO sellerDTO, String email, String token);

    Optional<SellerDetails> deleteSellerData(String email, String token);
}

package com.example.sellerdetails_bookstore_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class SellerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SellerId", nullable = false)
    private Long id;
    private String businessName;
    private String sellerName;
    private String profilePic;
    private String gstn;
    private String sellerWebsite;
    private String emailAddress;
    private String userName;
    private String password;
    private String contactNumber;
    private boolean verify;
    private LocalDateTime createdTimeStamp;
    private LocalDateTime updatedTimeStamp;

//    public SellerDetails(SellerDTO sellerDTO){
//        this.businessName = sellerDTO.getBusinessName();
//        this.sellerName = sellerDTO.getSellerName();
//        this.profilePic = sellerDTO.getProfilePic();
//        this.gstn = sellerDTO.getGstn();
//        this.sellerWebsite = sellerDTO.getSellerWebsite();
//        this.emailAddress = sellerDTO.getEmailAddress();
//        this.userName = sellerDTO.getUserName();
//        this.password = sellerDTO.getPassword();
//        this.contactNumber = sellerDTO.getContactNumber();
//        this.verify = sellerDTO.isVerify();
//    }
    public SellerDetails(String businessName, String sellerName, String profilePic, String gstn, String sellerWebsite, String emailAddress, String userName, String password, String contactNumber, boolean verify, LocalDateTime createdTime, LocalDateTime updatedTime){
        this.businessName = businessName;
        this.sellerName = sellerName;
        this.profilePic = profilePic;
        this.gstn = gstn;
        this.sellerWebsite = sellerWebsite;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.contactNumber = contactNumber;
        this.verify = verify;
        this.createdTimeStamp = createdTime;
        this.updatedTimeStamp = updatedTime;
    }
}

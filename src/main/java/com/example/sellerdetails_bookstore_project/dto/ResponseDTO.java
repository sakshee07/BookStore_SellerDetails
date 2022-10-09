package com.example.sellerdetails_bookstore_project.dto;

import com.example.sellerdetails_bookstore_project.model.SellerDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ResponseDTO {
    String message;
    Object response;
    public ResponseDTO(String message, String response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, SellerDetails response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, List<SellerDetails> response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, Optional<SellerDetails> response) {
        this.message = message;
        this.response = response;
    }
}

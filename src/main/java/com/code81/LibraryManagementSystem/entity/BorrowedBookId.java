package com.code81.LibraryManagementSystem;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BorrowedBookId implements Serializable {
    private Integer transactionId;
    private Integer bookId;
}

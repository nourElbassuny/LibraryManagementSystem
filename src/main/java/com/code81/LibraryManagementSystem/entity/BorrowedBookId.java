package com.code81.LibraryManagementSystem.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BorrowedBookId implements Serializable {
    private Integer transactionId;
    private Integer bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowedBookId that)) return false;
        return transactionId.equals(that.transactionId) && bookId.equals(that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, bookId);
    }
}

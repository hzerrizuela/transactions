package ar.com.transactions.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    private Long id;

    private Double amount;

    private String msg;

    private LocalDateTime creationDate;
}

package ar.com.experta.api.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 */
@Entity
@Getter
@Setter
@Table(name = "transactions")
@NoArgsConstructor
@ToString
public class TransactionsDB {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column
    private Long id;

	@Column
	private Double amount;

	@Column
	private Double balance;

	@Column
	@CreationTimestamp
	private LocalDateTime creationDate;

	private Long idUser;

	public TransactionsDB(Double amount, Double balance, Long idUser) {
		this.amount = amount;
		this.balance = balance;
		this.idUser = idUser;
	}
}

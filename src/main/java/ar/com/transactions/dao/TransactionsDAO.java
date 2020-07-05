package ar.com.transactions.dao;

import ar.com.transactions.api.db.TransactionsDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsDAO extends CrudRepository<TransactionsDB, Long> {

    List<TransactionsDB>  findByIdUserOrderByCreationDateDesc(Long userId);

    Optional<TransactionsDB> findFirstByOrderByIdDesc();
}
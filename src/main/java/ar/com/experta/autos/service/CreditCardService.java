package ar.com.experta.autos.service;

import ar.com.experta.api.db.TransactionsDB;
import ar.com.experta.dao.TransactionsDAO;
import ar.com.experta.dto.TransactionDTO;
import ar.com.experta.exceptions.AccountBalanceException;
import ar.com.experta.exceptions.TransactionNotFoundException;
import ar.com.experta.model.TransactionType;
import ar.com.experta.resources.autos.request.RequestTx;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for Transactions
 */
@Service
@Slf4j
public class CreditCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);
    public static final String USER_ID = "11110000";

    @Autowired
    private TransactionsDAO transactionsDAO;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @return
     */
    public List<TransactionDTO> getTransactionsByUserId(Long userId) {
        List<TransactionsDB> transactions = transactionsDAO.findByIdUserOrderByCreationDateDesc(userId);
        List<TransactionDTO> transactionsDto = transactions.stream().map(t -> this.modelMapper.map(t, TransactionDTO.class)).collect(Collectors.toList());
        return transactionsDto;
    }

    /**
     * @param request
     * @return
     */
    public TransactionDTO create(RequestTx request) {
        boolean noneMatch = Stream.of(TransactionType.values()).noneMatch(v -> v.name().equals(request.getType()));
        if (noneMatch) {
            throw new AccountBalanceException("Invalid operation type");
        }
        double newBalance = 0d;
        if (request.getType().equals(TransactionType.DEBIT.name())) {
            if (getBalance() < request.getAmount()) {
                throw new AccountBalanceException("Insufficient funds - You can't withdraw that amount");
            }
            newBalance = getBalance() - request.getAmount();
        }
        if (request.getType().equals(TransactionType.CREDIT.name())) {
            newBalance = getBalance() + request.getAmount();
        }

        TransactionsDB tx = new TransactionsDB(request.getAmount(), newBalance, Long.parseLong(USER_ID));
        TransactionDTO txDto = this.modelMapper.map(transactionsDAO.save(tx), TransactionDTO.class);

        return txDto;

    }

    /**
     * @param idTx
     * @return
     */
    public TransactionDTO findById(Long idTx) {
        TransactionsDB transactionsDB = transactionsDAO.findById(idTx).orElseThrow(TransactionNotFoundException::new);
        TransactionDTO txDto = this.modelMapper.map(transactionsDB, TransactionDTO.class);
        return txDto;
    }

    private Double getBalance() {
        Optional<TransactionsDB> latestTx = transactionsDAO.findFirstByOrderByIdDesc();
        return latestTx.map(TransactionsDB::getBalance).orElse(0d);
    }
}


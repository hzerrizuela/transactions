package ar.com.experta.controllers;

import ar.com.experta.autos.service.CreditCardService;
import ar.com.experta.dto.TransactionDTO;
import ar.com.experta.exceptions.AccountBalanceException;
import ar.com.experta.exceptions.TransactionNotFoundException;
import ar.com.experta.resources.autos.request.RequestTx;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static ar.com.experta.autos.service.CreditCardService.USER_ID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping()
    @ApiOperation(value = "Fetches transactions history")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response ok"),
            @ApiResponse(code = 400, message = "Wrong request")})
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUser(@ApiParam(name = "userId",
            value = "User id",
            defaultValue = USER_ID,
            required = true) @RequestParam("userId") Long userId) {

        List<TransactionDTO> transactionsDto = creditCardService.getTransactionsByUserId(userId);

        return new ResponseEntity<>(transactionsDto, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "Commit new transaction to the account")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created tx"),
            @ApiResponse(code = 400, message = "Wrong request"),
            @ApiResponse(code = 502, message = "Service unavailable")})
    public ResponseEntity<TransactionDTO> createTransaction(@ApiParam(name = "RequestTx",
            value = "Transaction request", required = true) @Valid @RequestBody RequestTx request) {

        TransactionDTO txDto = new TransactionDTO();
        try {
            txDto = creditCardService.create(request);

        } catch (AccountBalanceException e) {
            txDto.setMsg(e.getMessage());
            return new ResponseEntity<>(txDto, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(txDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response ok"),
            @ApiResponse(code = 404, message = "Not found")})
    public ResponseEntity<TransactionDTO> getTransactionsById(@ApiParam(name = "id",
            value = "ID Tx", required = true) @PathVariable Long id) {

        TransactionDTO txDto = new TransactionDTO();
        try {
            txDto = creditCardService.findById(id);

        } catch (TransactionNotFoundException e) {
            return new ResponseEntity<>(txDto, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(txDto, HttpStatus.OK);
    }

}

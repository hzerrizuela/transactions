package ar.com.experta.resources.autos.request;

import ar.com.experta.model.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class RequestTx implements Serializable {

//    @NotEmpty
    @Positive
    private Double amount;
    @NotEmpty
    private String type;


    @ApiModelProperty(required = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ApiModelProperty(required = true)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}


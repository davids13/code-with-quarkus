package persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
public class Account extends PanacheEntity {

    @NotBlank(message = "Account holder name cannot be blank")
    public String accountHolderName;
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be positive")
    public BigDecimal balance;

    public Account() {
    }

    public Account(String accountHolderName, BigDecimal balance) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }
}

package persistence;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    AccountService accountService;

    @Inject
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @POST
    public Response createAccount(@Valid Account account) {
        return Response.status(201).entity(accountService.createAccount(account)).build();
    }

    @GET
    public List<Account> all() {
        return accountService.getAllAccounts();
    }

    @POST
    @Path("/{id}/deposit")
    public Response deposit(@PathParam("id") Long id, AmountDTO dto) {
        try {
            return Response.ok(accountService.deposit(id, dto.amount)).build();
        } catch (Exception e) {
            return Response.status(400).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    @POST
    @Path("/{id}/withdraw")
    public Response withdraw(@PathParam("id") Long id, AmountDTO dto) {
        try {
            return Response.ok(accountService.withdraw(id, dto.amount)).build();
        } catch (Exception e) {
            return Response.status(400).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    @POST
    @Path("/transfer")
    public Response transfer(TransferRequestDTO dto) {
        try {
            accountService.transferFunds(dto.fromAccountId, dto.toAccountId, dto.amount);
            return Response.ok(new SuccessMessage("Transfer complete")).build();
        } catch (RuntimeException e) {
            return Response.status(500).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    public static class AmountDTO {
        public BigDecimal amount;
    }

    public static class TransferRequestDTO {
        public Long fromAccountId;
        public Long toAccountId;
        public BigDecimal amount;
    }

    public static class ErrorMessage {
        public String error;

        public ErrorMessage(String error) {
            this.error = error;
        }
    }

    public static class SuccessMessage {
        public String message;

        public SuccessMessage(String msg) {
            this.message = msg;
        }
    }
}

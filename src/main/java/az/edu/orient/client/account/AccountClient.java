package az.edu.orient.client.account;

import az.edu.orient.client.account.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accountClient", url = "${client.account.url}/internal/accounts")
public interface AccountClient {

    @PostMapping
    AccountDto createAccount(@RequestBody AccountDto accountDto);
}

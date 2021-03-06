package service.account;

import model.Account;

import java.util.List;

public interface AccountService {
    List<Account> findByClientId(Long id);

    boolean save(Account account);

    boolean update(Account account);

    boolean updateBalance(Account account, Long balance);

    boolean delete(Account account);
}

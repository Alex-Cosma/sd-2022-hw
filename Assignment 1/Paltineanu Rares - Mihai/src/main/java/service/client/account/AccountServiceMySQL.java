package service.client.account;

import model.Account;
import model.Client;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import service.client.ClientService;

import java.util.List;

public class AccountServiceMySQL implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findall() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public boolean save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean update(Long id, Account newAccount) {

        return accountRepository.update(id, newAccount);
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

    @Override
    public boolean delete(Long id) {
        return accountRepository.delete(id);
    }

    @Override
    public boolean transferMoney(Long id, int money) {
        return accountRepository.transferMoney(id, money);
    }
}

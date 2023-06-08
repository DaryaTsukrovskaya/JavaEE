package by.teachmeskills.ps.service;

import by.teachmeskills.ps.exceptions.BankAccountNotAddedException;
import by.teachmeskills.ps.exceptions.MerchantNotFoundException;
import by.teachmeskills.ps.exceptions.NoBankAccountsFoundException;
import by.teachmeskills.ps.model.AccountStatus;
import by.teachmeskills.ps.model.BankAccount;
import by.teachmeskills.ps.model.Merchant;
import by.teachmeskills.ps.utils.CRUDUtils;

import java.util.List;
import java.util.stream.Stream;

public class MerchantService {
        public BankAccount addBankAccount(Merchant merchant, BankAccount bankAccount) throws BankAccountNotAddedException {
            boolean correctBankAccountNumber = Stream.of(bankAccount).anyMatch(c -> bankAccount.getAccountNumber().length() == 8 && bankAccount.getAccountNumber().matches("^[0-9]*$"));
            if (!correctBankAccountNumber) {
                throw new BankAccountNotAddedException("The length of account number should be eight digits without other characters!");
            }
            List<BankAccount> bankAccounts = CRUDUtils.getMerchantBankAccounts(merchant);
            BankAccount foundBankAccount = null;
            if (!bankAccounts.isEmpty()) {
                foundBankAccount = bankAccounts.stream().filter(b -> b.getAccountNumber().equals(bankAccount.getAccountNumber())).findFirst().get();
            }
            if (foundBankAccount != null) {
                if (foundBankAccount.getStatus() == AccountStatus.DELETED) {
                    foundBankAccount.setStatus(AccountStatus.ACTIVE);
                    CRUDUtils.updateMerchantBankAccountStatus(foundBankAccount);
                }
            } else {
                CRUDUtils.createBankAccount(bankAccount);
            }

            return bankAccount;
        }

        public List<BankAccount> getMerchantBankAccounts(Merchant merchant) throws NoBankAccountsFoundException {
            List<BankAccount> bankAccounts = CRUDUtils.getMerchantBankAccounts(merchant);
            if (bankAccounts.isEmpty()) {
                throw new NoBankAccountsFoundException("This merchant does not have bank accounts");
            }
            return bankAccounts;
        }

        public BankAccount updateBankAccount(String id, String newId) throws NoBankAccountsFoundException {
            BankAccount bankAccount1 = CRUDUtils.getBankAccountById(id);
            if (bankAccount1 == null) {
                throw new NoBankAccountsFoundException("Bank account not found");
            } else {
                CRUDUtils.updateMerchantBankAccountNumber(bankAccount1, newId);
            }
            return CRUDUtils.getBankAccountById(newId);
        }

        public void deleteBankAccount(String id) {
            CRUDUtils.deleteBankAccountById(id);
        }

        public Merchant createMerchant(Merchant merchant) {
            CRUDUtils.createMerchant(merchant);
            Merchant merchant1 = CRUDUtils.getMerchantById(merchant.getId());
            return merchant1;
        }

        public List<Merchant> getMerchants() throws MerchantNotFoundException {
            List<Merchant> merchants = CRUDUtils.getAllMerchants();
            if (merchants.isEmpty()) {
                throw new MerchantNotFoundException("Merchants not found");
            }
            return merchants;
        }

        public Merchant getMerchant(String id) throws MerchantNotFoundException {
            Merchant merchant = CRUDUtils.getMerchantById(id);
            if (merchant == null) {
                throw new MerchantNotFoundException("Merchant not found");
            }
            return merchant;
        }

        public void deleteMerchant(String id) throws MerchantNotFoundException {
            Merchant merchant = CRUDUtils.getMerchantById(id);
            if (merchant == null) {
                throw new MerchantNotFoundException("Merchant not found");
            }
            CRUDUtils.deleteMerchantById(id);
        }

    }

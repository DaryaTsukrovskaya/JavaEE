package by.teachmeskills.ps.utils;

import by.teachmeskills.ps.model.AccountStatus;
import by.teachmeskills.ps.model.BankAccount;
import by.teachmeskills.ps.model.Merchant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String GET_MERCHANT_BANK_ACCOUNTS_QUERY = "SELECT * FROM bank_accounts WHERE merchant_id = ? order by status ASC, created_at ASC";
    private static final String UPDATE_MERCHANT_BANK_ACCOUNT_NUMBER_QUERY = "UPDATE bank_accounts SET account_number=? WHERE merchant_id = ?";
    private static final String UPDATE_MERCHANT_BANK_ACCOUNT_STATUS_QUERY = "UPDATE bank_accounts SET status=? WHERE merchant_id = ?";
    private static final String CREATE_BANK_ACCOUNT_QUERY = "INSERT INTO bank_accounts(id,merchant_id,status,account_number,created_at) VALUES(?,?,?,?,?)";
    private static final String GET_BANK_ACCOUNT_BY_ID_QUERY = "SELECT * FROM bank_accounts WHERE id=? ";
    private static final String DELETE_BANK_ACCOUNT_BY_ID = "DELETE FROM bank_accounts WHERE id=?";
    private static final String CREATE_MERCHANT_QUERY = "INSERT INTO merchants(id,name,created_at) VALUES(?,?,?)";
    private static final String GET_ALL_MERCHANTS_QUERY = "SELECT * FROM merchants";
    private static final String GET_MERCHANT_BY_ID_QUERY = "SELECT * FROM merchants WHERE id=?";
    private static final String DELETE_MERCHANT_BY_ID_QUERY = "DELETE FROM merchants WHERE id=?";

    public static List<BankAccount> getMerchantBankAccounts(Merchant merchant) {
        List<BankAccount> bankAccounts = new ArrayList();

        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(GET_MERCHANT_BANK_ACCOUNTS_QUERY)) {

            statement.setString(1, merchant.getId());
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                bankAccounts.add(new BankAccount(set.getString(1), set.getString(2), ConverterUtils.toAccountStatus(set.getString(3)), EncryptionUtils.decrypt(set.getString(4)), set.getTimestamp(5).toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bankAccounts;
    }

    public static void updateMerchantBankAccountNumber(BankAccount bankAccount, String newBANumber) {
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_MERCHANT_BANK_ACCOUNT_NUMBER_QUERY)) {
            statement.setString(1, newBANumber);
            statement.setString(2, bankAccount.getMerchantId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateMerchantBankAccountStatus(BankAccount bankAccount) {
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_MERCHANT_BANK_ACCOUNT_STATUS_QUERY)) {
            statement.setString(1, bankAccount.getStatus().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createBankAccount(BankAccount bankAccount) {
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(CREATE_BANK_ACCOUNT_QUERY)) {
            statement.setString(1, bankAccount.getId());
            statement.setString(2, bankAccount.getMerchantId());
            statement.setString(3, bankAccount.getStatus().toString());
            statement.setString(4, EncryptionUtils.encrypt(bankAccount.getAccountNumber()));
            statement.setTimestamp(5, Timestamp.valueOf(bankAccount.getCreatedAt()));
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static BankAccount getBankAccountById(String id) {
        BankAccount bankAccount = null;

        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(GET_BANK_ACCOUNT_BY_ID_QUERY)) {
            statement.setString(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String returnedId = set.getString("id");
                String merchantId = set.getString("merchant_id");
                AccountStatus status = ConverterUtils.toAccountStatus(set.getString("status"));
                String accNumber = EncryptionUtils.decrypt(set.getString("account_number"));
                LocalDateTime createdAt = set.getTimestamp("created_at").toLocalDateTime();
                bankAccount = new BankAccount(returnedId, merchantId, status, accNumber, createdAt);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bankAccount;
    }

    public static void deleteBankAccountById(String id) {
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_BANK_ACCOUNT_BY_ID)) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createMerchant(Merchant merchant) {
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(CREATE_MERCHANT_QUERY)) {
            statement.setString(1, merchant.getId());
            statement.setString(2, merchant.getName());
            statement.setTimestamp(3, Timestamp.valueOf(merchant.getCreatedAt()));
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static List<Merchant> getAllMerchants() {
        List<Merchant> merchants = new ArrayList();
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(GET_ALL_MERCHANTS_QUERY)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                merchants.add(new Merchant(set.getString(1), set.getString(2), set.getTimestamp(3).toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return merchants;
    }

    public static Merchant getMerchantById(String id) {
        Merchant merchant = null;
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(GET_MERCHANT_BY_ID_QUERY)) {
            statement.setString(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String returnedId = set.getString("id");
                String name = set.getString("name");
                LocalDateTime localDateTime = set.getTimestamp("created_at").toLocalDateTime();
                merchant = new Merchant(returnedId, name, localDateTime);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return merchant;
    }

    public static void deleteMerchantById(String id) {
        try (Connection connection = DbUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_MERCHANT_BY_ID_QUERY)) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

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

        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM bank_accounts WHERE merchant_id = ? order by status ASC, created_at ASC");

                try {
                    statement.setString(1, merchant.getId());
                    ResultSet set = statement.executeQuery();

                    while (set.next()) {
                        bankAccounts.add(new BankAccount(set.getString(1), set.getString(2), ConverterUtils.toAccountStatus(set.getString(3)), EncryptionUtils.decrypt(set.getString(4)), set.getTimestamp(5).toLocalDateTime()));
                    }
                } catch (Throwable var8) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var9) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var6) {
                        var9.addSuppressed(var6);
                    }
                }

                throw var9;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var10) {
            System.out.println(var10.getMessage());
        }

        return bankAccounts;
    }

    public static void updateMerchantBankAccountNumber(BankAccount bankAccount, String newBANumber) {
        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("UPDATE bank_accounts SET account_number=? WHERE merchant_id = ?");

                try {
                    statement.setString(1, newBANumber);
                    statement.setString(2, bankAccount.getMerchantId());
                    statement.executeUpdate();
                } catch (Throwable var8) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var9) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var6) {
                        var9.addSuppressed(var6);
                    }
                }

                throw var9;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var10) {
            System.out.println(var10.getMessage());
        }

    }

    public static void updateMerchantBankAccountStatus(BankAccount bankAccount) {
        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("UPDATE bank_accounts SET status=? WHERE merchant_id = ?");

                try {
                    statement.setString(1, bankAccount.getStatus().toString());
                    statement.executeUpdate();
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var9) {
            System.out.println(var9.getMessage());
        }

    }

    public static void createBankAccount(BankAccount bankAccount) {
        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO bank_accounts(id,merchant_id,status,account_number,created_at) VALUES(?,?,?,?,?)");

                try {
                    statement.setString(1, bankAccount.getId());
                    statement.setString(2, bankAccount.getMerchantId());
                    statement.setString(3, bankAccount.getStatus().toString());
                    statement.setString(4, EncryptionUtils.encrypt(bankAccount.getAccountNumber()));
                    statement.setTimestamp(5, Timestamp.valueOf(bankAccount.getCreatedAt()));
                    statement.execute();
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var9) {
            System.out.println(var9.getMessage());
        }

    }

    public static BankAccount getBankAccountById(String id) {
        BankAccount bankAccount = null;

        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM bank_accounts WHERE id=? ");

                try {
                    statement.setString(1, id);

                    String returnedId;
                    String merchantId;
                    AccountStatus status;
                    String accNumber;
                    LocalDateTime createdAt;
                    for (ResultSet set = statement.executeQuery(); set.next(); bankAccount = new BankAccount(returnedId, merchantId, status, accNumber, createdAt)) {
                        returnedId = set.getString("id");
                        merchantId = set.getString("merchant_id");
                        status = ConverterUtils.toAccountStatus(set.getString("status"));
                        accNumber = EncryptionUtils.decrypt(set.getString("account_number"));
                        createdAt = set.getTimestamp("created_at").toLocalDateTime();
                    }
                } catch (Throwable var12) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var11) {
                            var12.addSuppressed(var11);
                        }
                    }

                    throw var12;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var13) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var10) {
                        var13.addSuppressed(var10);
                    }
                }

                throw var13;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var14) {
            System.out.println(var14.getMessage());
        }

        return bankAccount;
    }

    public static void deleteBankAccountById(String id) {
        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM bank_accounts WHERE id=?");

                try {
                    statement.setString(1, id);
                    statement.execute();
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var9) {
            System.out.println(var9.getMessage());
        }

    }

    public static void createMerchant(Merchant merchant) {
        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO merchants(id,name,created_at) VALUES(?,?,?)");

                try {
                    statement.setString(1, merchant.getId());
                    statement.setString(2, merchant.getName());
                    statement.setTimestamp(3, Timestamp.valueOf(merchant.getCreatedAt()));
                    statement.execute();
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var9) {
            System.out.println(var9.getMessage());
        }

    }

    public static List<Merchant> getAllMerchants() {
        List<Merchant> merchants = new ArrayList();

        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM merchants");

                try {
                    ResultSet set = statement.executeQuery();

                    while (set.next()) {
                        merchants.add(new Merchant(set.getString(1), set.getString(2), set.getTimestamp(3).toLocalDateTime()));
                    }
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var9) {
            System.out.println(var9.getMessage());
        }

        return merchants;
    }

    public static Merchant getMerchantById(String id) {
        Merchant merchant = null;

        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM merchants WHERE id=?");

                try {
                    statement.setString(1, id);

                    String returnedId;
                    String name;
                    LocalDateTime localDateTime;
                    for (ResultSet set = statement.executeQuery(); set.next(); merchant = new Merchant(returnedId, name, localDateTime)) {
                        returnedId = set.getString("id");
                        name = set.getString("name");
                        localDateTime = set.getTimestamp("created_at").toLocalDateTime();
                    }
                } catch (Throwable var10) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var9) {
                            var10.addSuppressed(var9);
                        }
                    }

                    throw var10;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var11) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var8) {
                        var11.addSuppressed(var8);
                    }
                }

                throw var11;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var12) {
            System.out.println(var12.getMessage());
        }

        return merchant;
    }

    public static void deleteMerchantById(String id) {
        try {
            Connection connection = DbUtils.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM merchants WHERE id=?");

                try {
                    statement.setString(1, id);
                    statement.execute();
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var9) {
            System.out.println(var9.getMessage());
        }

    }
}

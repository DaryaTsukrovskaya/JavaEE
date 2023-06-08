package by.teachmeskills.ps.model;

import java.time.LocalDateTime;

public class BankAccount {
        private String id;
        private String merchantId;
        private AccountStatus status;
        private String accountNumber;
        private LocalDateTime createdAt;

        public BankAccount(String id, String merchantId, AccountStatus status, String accountNumber, LocalDateTime createdAt) {
            this.id = id;
            this.merchantId = merchantId;
            this.status = status;
            this.accountNumber = accountNumber;
            this.createdAt = createdAt;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMerchantId() {
            return this.merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public AccountStatus getStatus() {
            return this.status;
        }

        public void setStatus(AccountStatus status) {
            this.status = status;
        }

        public String getAccountNumber() {
            return this.accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public LocalDateTime getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public String toString() {
            return "BankAccount{id='" + this.id + "', merchantId='" + this.merchantId + "', status=" + this.status + ", accountNumber='" + this.accountNumber + "', createdAt=" + this.createdAt + "}";
        }
}

package by.teachmeskills;


import by.teachmeskills.ps.exceptions.BankAccountNotAddedException;
import by.teachmeskills.ps.exceptions.MerchantNotFoundException;
import by.teachmeskills.ps.exceptions.NoBankAccountsFoundException;
import by.teachmeskills.ps.model.AccountStatus;
import by.teachmeskills.ps.model.BankAccount;
import by.teachmeskills.ps.model.Merchant;
import by.teachmeskills.ps.service.MerchantService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static MerchantService merchantService = new MerchantService();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter anything other than zero to get started.");
        int choice = scan.nextInt();
        while (choice != 0) {
            System.out.println("Please enter the number corresponding to what you want to do:");
            System.out.println("""
                    1- to add bank account
                    2- to get merchant bank accounts
                    3- to update bank account
                    4- delete bank account
                    5- create merchant
                    6- get all merchants
                    7- get merchant by id
                    8- delete merchant
                    0- exit""");
            choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter merchant id:");
                    String id = scan.next();
                    System.out.println("Enter bankAccount id:");
                    String accId = scan.next();
                    System.out.println("Enter account number (8 numbers):");
                    String accNum = scan.next();
                    try {
                        Merchant merchant = merchantService.getMerchant(id);
                        BankAccount bankAccount = new BankAccount(accId, merchant.getId(), AccountStatus.ACTIVE, accNum, LocalDateTime.now());
                        merchantService.addBankAccount(merchant, bankAccount);
                    } catch (MerchantNotFoundException | BankAccountNotAddedException e) {
                        System.out.println(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter merchant id:");
                    String id = scan.next();
                    try {
                        Merchant merchant = merchantService.getMerchant(id);
                        List<BankAccount> bankAccounts = merchantService.getMerchantBankAccounts(merchant);
                        for (int i = 0; i < bankAccounts.size(); i++) {
                            System.out.println(bankAccounts.get(i).toString());
                        }
                    } catch (MerchantNotFoundException | NoBankAccountsFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Enter bank account id for updating:");
                    String id = scan.next();
                    System.out.println("Enter new bank account number:");
                    String newNum = scan.next();
                    try {
                        merchantService.updateBankAccount(id, newNum);
                    } catch (NoBankAccountsFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("Enter bank account id for deleting:");
                    String id = scan.next();
                    merchantService.deleteBankAccount(id);
                }
                case 5 -> {
                    System.out.println("Enter merchant id:");
                    String id = scan.next();
                    System.out.println("Enter merchant name:");
                    String name = scan.next();
                    Merchant merchant = new Merchant(id, name, LocalDateTime.now());
                    merchantService.createMerchant(merchant);
                }
                case 6 -> {
                    try {
                        List<Merchant> merchants = merchantService.getMerchants();
                        merchants.forEach(System.out::println);
                    } catch (MerchantNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 7 -> {
                    System.out.println("Enter merchant id: ");
                    String id = scan.next();
                    try {
                        Merchant merchant = merchantService.getMerchant(id);
                        System.out.println(merchant.toString());
                    } catch (MerchantNotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case 8 -> {
                    System.out.println("Enter merchant id: ");
                    String id = scan.next();
                    try {
                        merchantService.deleteMerchant(id);
                    } catch (MerchantNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }

                default -> throw new IllegalArgumentException("Unrecognised menu item");
            }
        }
    }
}


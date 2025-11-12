import Exceptions.EmptyAccountHolder;
import Exceptions.IncorrectCVVException;
import Exceptions.IncorretCardNumberException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Payment payment;
        System.out.println("Please, select the payment method. Select 1 by credit card");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
            case 1:
                payment = new CreditCardPayment();

                if (payment instanceof CreditCardPayment) {

                    CreditCardPayment cd = (CreditCardPayment) payment;
                    try {
                        System.out.println("write your credit card number:");
                        String user_credit_card_number = sc.nextLine();

                        cd.setCardnumber(user_credit_card_number);

                        StringBuilder formatted = new StringBuilder();
                        //loop through every character in the entered credit card number
                        for (int i = 0; i < user_credit_card_number.length(); i++) {
                            formatted.append(user_credit_card_number.charAt(i));
                             // After every 4 digits, insert a space — except after the last group
                            if ((i + 1) % 4 == 0 && i != user_credit_card_number.length() - 1) {
                                formatted.append(" ");
                            }

                        }
                        System.out.println("card number:" + formatted);
                    } catch (IncorretCardNumberException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        System.out.println("Who is the account holder?");
                        String account_holder = sc.nextLine();
                        cd.setAccountholder(account_holder);
                    } catch (EmptyAccountHolder e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        System.out.println("write the cvv of your card");
                        String cvv = sc.nextLine();
                        cd.setCvv(cvv);
                    } catch (IncorrectCVVException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("write the expiry date of your card");
                    String expiry_date = sc.nextLine();
                    cd.setExpiry_date(expiry_date);

                    System.out.println("how much do you want to pay?");
                    double amount = sc.nextDouble();
                    cd.pay(amount);
                    cd.finalizing_payment();
                    try (FileWriter fw = new FileWriter("Credit_Card.txt", true)) {
                        LocalDateTime dt = LocalDateTime.now();
                        fw.write("Card Number:" + cd.getCardnumber() + " " + System.lineSeparator());
                        fw.write("Account Holder:" + cd.getAccountholder() + " " + System.lineSeparator());

                        fw.write("cvv:" + cd.getCvv() + " " + System.lineSeparator());

                        fw.write("Expiry date:" + cd.getExpiry_date() + "" + System.lineSeparator());
                        fw.write("-------");
                        fw.write("Payment made by credit card!");
                        fw.write("Payment made on:" + dt);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

                break;
        }
    }
}
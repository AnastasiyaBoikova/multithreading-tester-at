package multithreading.tester.at.service;

import multithreading.tester.at.model.Account;

public class MoneyTransferService {

    public void transfer(Account from, Account to, Long amount) {

        if (from.getBalance() >= amount) {
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
        } else {
            throw new IllegalArgumentException("Недостаточно денег");
        }
    }

//        Long first;
//        Long second;
//
//        if(from.getId()> to.getId()){
//            first= from.getId();
//            second= to.getId();
//        }else {
//            first= to.getId();
//            second= from.getId();
//        }
//
//        synchronized (first) {
//            synchronized (second) {
//                if (from.getBalance() >= amount) {
//                    from.setBalance(from.getBalance() - amount);
//                    to.setBalance(to.getBalance() + amount);
//                } else {
//                    throw new IllegalArgumentException("Недостаточно денег");
//                }
//            }
//        }
//    }

}
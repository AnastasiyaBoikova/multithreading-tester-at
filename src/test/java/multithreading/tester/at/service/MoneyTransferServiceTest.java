package multithreading.tester.at.service;

import multithreading.tester.at.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferServiceTest {

    @Test
    void transfer() throws InterruptedException, ExecutionException {

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        Account from = new Account(1L, 1_000L);
        Account to = new Account(2L, 1_000L);
        Lock locker = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<MoneyTransferTask> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(new MoneyTransferTask(moneyTransferService, from, to, 1L, locker));
            tasks.add(new MoneyTransferTask(moneyTransferService, to, from, 1L, locker));

        }
        List<Future<Long>> futures = executorService.invokeAll(tasks);
        for (Future<Long> future : futures) {
            future.get();

        }

        Assertions.assertEquals(from.getBalance(), 1000L);
        Assertions.assertEquals(to.getBalance(), 1000L);

    }

    static class MoneyTransferTask implements Callable<Long> {

        private MoneyTransferService moneyTransferService;
        private Account from;
        private Account to;
        private Long amount;
        Lock locker;

        public MoneyTransferTask(MoneyTransferService moneyTransferService, Account from, Account to, Long amount, Lock locker) {
            this.moneyTransferService = moneyTransferService;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.locker = locker;
        }

        @Override
        public Long call() throws Exception {

            locker.lock();
            try {
                moneyTransferService.transfer(from, to, amount);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }

            return from.getBalance();
        }
    }
}
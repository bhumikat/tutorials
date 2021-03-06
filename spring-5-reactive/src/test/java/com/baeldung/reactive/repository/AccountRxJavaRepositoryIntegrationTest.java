package com.baeldung.reactive.repository;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class AccountRxJavaRepositoryIntegrationTest {

    @Autowired
    AccountRxJavaRepository repository;

    @Test
    public void givenValue_whenFindAllByValue_thenFindAccounts() {
        repository.save(new Account(null, "bruno", 12.3)).blockingGet();
        Observable<Account> accountObservable = repository.findAllByValue(12.3);
        Account account = accountObservable.filter(x -> x.getOwner().equals("bruno")).blockingFirst();
        assertEquals("bruno", account.getOwner());
        assertEquals(Double.valueOf(12.3), account.getValue());
        assertNotNull(account.getId());
    }

    @Test
    public void givenOwner_whenFindFirstByOwner_thenFindAccount() {
        repository.save(new Account(null, "bruno", 12.3)).blockingGet();
        Single<Account> accountSingle = repository.findFirstByOwner(Single.just("bruno"));
        Account account = accountSingle.blockingGet();
        assertEquals("bruno", account.getOwner());
        assertEquals(Double.valueOf(12.3), account.getValue());
        assertNotNull(account.getId());
    }

}
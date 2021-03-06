package ru.mray.core.controller

import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.core.env.Environment
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.ui.ExtendedModelMap
import ru.mray.core.model.Account
import ru.mray.core.model.Transaction
import ru.mray.core.repository.AccountRepository
import ru.mray.core.repository.TransactionRepository
import ru.mray.core.service.ConfigService
import ru.mray.core.service.MailService
import java.time.Period
import javax.servlet.http.HttpServletResponse

class TestBuyController {
    @Test
    fun testProcessForm() {
        val accountRepository = Mockito.mock(AccountRepository::class.java)
        val transactionRepository = Mockito.mock(TransactionRepository::class.java)
        val response = Mockito.mock(HttpServletResponse::class.java)
        val mailService = Mockito.mock(MailService::class.java)
        val env = Mockito.mock(Environment::class.java)
        val configService = Mockito.mock(ConfigService::class.java)
        val model = ExtendedModelMap()

        val buyController = JoinController(accountRepository, transactionRepository, BCryptPasswordEncoder(), mailService, configService, env)
        val viewName = buyController.processForm(
                "bob@example.com",
                Account.Region.PH,
                3,
                response,
                model
        )
        Assert.assertEquals("join/done", viewName)

        val accountCaptor = ArgumentCaptor.forClass(Account::class.java)
        verify(accountRepository).save(accountCaptor.capture())

        val account = accountCaptor.value
        Assert.assertEquals("bob@example.com", account.email)
        Assert.assertEquals(3, account.renewPeriod)
        Assert.assertEquals(Account.Region.PH,account.region)
        Assert.assertEquals(null, account.familyToken)

        val transactionCaptor = ArgumentCaptor.forClass(Transaction::class.java)
        verify(transactionRepository, times(1)).save(transactionCaptor.capture()) // There was a 1-day bonus transaction before
        transactionCaptor.allValues.forEach { transaction ->
            Assert.assertEquals(account.id, transaction.account.id)
            Assert.assertEquals(account.region, transaction.region)

            when(transaction.type) {
                Transaction.TransactionType.BONUS -> {
                    Assert.assertEquals(Period.ofDays(1), transaction.period)
                    Assert.assertNotNull(transaction.paidAt)
                }
                Transaction.TransactionType.PAYMENT -> {
                    Assert.assertEquals(Period.ofMonths(account.renewPeriod), transaction.period)
                    Assert.assertNull(transaction.paidAt)
                }
            }
        }

    }
}
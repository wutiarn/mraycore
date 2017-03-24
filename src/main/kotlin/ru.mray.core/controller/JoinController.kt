package ru.mray.core.controller

import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import ru.mray.core.model.Account
import ru.mray.core.model.Transaction
import ru.mray.core.repository.AccountRepository
import ru.mray.core.repository.TransactionRepository
import java.time.Instant
import java.time.Period
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/join")
class JoinController(val accountRepository: AccountRepository,
                     val transactionRepository: TransactionRepository,
                     val passwordEncoder: PasswordEncoder) {

    val logger: Logger = LoggerFactory.getLogger(JoinController::class.java)

    @RequestMapping
    fun getPage(): String {
        return "join/join"
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun processForm(@RequestParam email: String,
                    @RequestParam region: Account.Region,
                    @RequestParam period: Int,
                    httpServletResponse: HttpServletResponse,
                    model: Model): String {

        if (accountRepository.findByEmail(email) != null) {
            httpServletResponse.status = 400
            model.addAttribute("message", "Этот email уже связан с другим акканутом")
            return "join/error" // TODO: Create error page
        }

        val account = Account(email, region, period)
        val password = RandomStringUtils.random(8, true, true)
        account._password = passwordEncoder.encode(password)
        accountRepository.save(account)
        logger.info("New account: Email: $email. Region: $region. Period: $period")

        val bonusTransaction = Transaction(account.id, account.region, Period.ofDays(1), Transaction.TransactionType.BONUS)
        bonusTransaction.paidAt = Instant.now()

        val paymentTransaction = Transaction(account.id, account.region, Period.ofMonths(period), Transaction.TransactionType.PAYMENT)

        listOf(bonusTransaction, paymentTransaction).forEach { transaction ->
            transactionRepository.save(transaction)
            logger.info("New transaction: Type: ${transaction.type}. Account: ${transaction.accountId}. " +
                    "Period: ${transaction.period}. Region: ${transaction.region}. ID: ${transaction.id}")
        }

        model.addAttribute("email", email)
        model.addAttribute("transactionId", paymentTransaction.id)
        return "join/done"
    }
}
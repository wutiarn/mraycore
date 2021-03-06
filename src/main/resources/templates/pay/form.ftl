<#-- @ftlvariable name="WMI_CUSTOMER_EMAIL" type="java.lang.String" -->
<#-- @ftlvariable name="paymentsEnabled" type="java.lang.Boolean" -->
<#-- @ftlvariable name="pendingAccounts" type="java.lang.Number" -->
<#-- @ftlvariable name="unassignedTokensCount" type="java.lang.Number" -->
<#-- @ftlvariable name="account" type="ru.mray.core.model.Account" -->
<#-- @ftlvariable name="WMI_SIGNATURE" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_PAYMENT_NO" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_FAIL_URL" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_SUCCESS_URL" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_DESCRIPTION" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_CURRENCY_ID" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_PAYMENT_AMOUNT" type="java.lang.String" -->
<#-- @ftlvariable name="WMI_MERCHANT_ID" type="java.lang.String" -->
<#include '../library/standardPage.ftl'>

<@standardPage title="Оплата подписки">
<main>

    <div class="container">
        <div>К оплате: ${WMI_PAYMENT_AMOUNT}</div>
        <br>
        <#if !account.familyToken??>
            <div>
                <#if unassignedTokensCount gt 0>
                    В данный момент у нас свободно слотов: ${unassignedTokensCount}. Если вы оплатите прямо сейчас,
                    то, вероятно, сразу же получите приглашение в семью.
                <#else>
                    К сожалению, для выбранной страны у нас временно нет свободных слотов. Но если вы оплатите сейчас,
                    то одним из первых (в порядке очереди) получите приглашение.<br><br>

                    Не забывайте, что мы регистрируем семьи на деньги, что получаем от вас.
                    Вы всегда можете запросить возврат, просто написав в поддержку.<br><br>

                    Количество человек в очереди перед вами: ${pendingAccounts}. Позже этот счетчик вы сможете
                    найти в личном кабинете.<br><br>

                    Следить за наличием свободных слотов можно на <a href="/stats">странице статистики</a>.<br><br>

                    UPD: Из-за изменившейся политики добавления в семью Spotify доставка инвайта может занять
                    продолжительное время - вплоть до недели. Если период ожидания перешёл все разумные пределы,
                    напишите нам на support@music-ray.ru, и мы вернём вам деньги. Проверьте папку "Спам", инвайт
                    мог оказаться там.
                </#if>
            </div>
            <br>
        </#if>
        <form method="post" action="https://wl.walletone.com/checkout/checkout/Index">
            <input name="WMI_MERCHANT_ID" type="hidden" value="${WMI_MERCHANT_ID}"/>
            <input name="WMI_PAYMENT_AMOUNT" type="hidden" value="${WMI_PAYMENT_AMOUNT}"/>
            <input name="WMI_CURRENCY_ID" type="hidden" value="${WMI_CURRENCY_ID}"/>
            <input name="WMI_DESCRIPTION" type="hidden" value='${WMI_DESCRIPTION}'/>
            <input name="WMI_CUSTOMER_EMAIL" type="hidden" value="${WMI_CUSTOMER_EMAIL}"/>
            <input name="WMI_SUCCESS_URL" type="hidden" value="${WMI_SUCCESS_URL}"/>
            <input name="WMI_FAIL_URL" type="hidden" value="${WMI_FAIL_URL}"/>
            <input name="WMI_PAYMENT_NO" type="hidden" value="${WMI_PAYMENT_NO}"/>
            <input name="WMI_SIGNATURE" type="hidden" value="${WMI_SIGNATURE}"/>
            <input type="submit" <#if !paymentsEnabled>style="display: none"</#if> value="Оплатить">

            <#if !paymentsEnabled>
                <div>Оплата временно недоступна. Следите за новостями в <a href="https://t.me/music_ray">Telegram</a>.</div>
            </#if>
        </form>
    </div>
</main>
</@standardPage>
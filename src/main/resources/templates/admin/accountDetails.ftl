<#-- @ftlvariable name="transactions" type="ru.mray.core.model.Transaction[]" -->
<#-- @ftlvariable name="account" type="ru.mray.core.model.Account" -->
<#-- @ftlvariable name="title" type="java.lang.String" -->
<#include 'library/standardAdminPage.ftl'>

<@standardAdminPage title="Account details">
<p>
<table border="1">
    <tr>
        <td>Email</td>
        <td>${account.email}</td>
    </tr>
    <tr>
        <td>Id</td>
        <td>${account.id}</td>
    </tr>
    <tr>
        <td>Period</td>
        <td>${account.renewPeriod}</td>

    </tr>
    <tr>
        <td>Region</td>
        <td>${account.region}</td>
    </tr>
    <tr>
        <td>Token</td>
        <#if account.familyToken??>
            <td><a href="/admin/families/byToken/${account.familyToken}">${account.familyToken}</a></td>
        </#if>
    </tr>
    <tr>
        <td>Active until</td>
        <td>${account.activeUntil!}</td>
    </tr>
</table>
<p>
<table border="1">
    <tr>
        <td>Id</td>
        <td>Period</td>
        <td>Type</td>
        <td>Issue date</td>
        <td>PaidAt</td>
        <td>Activated at</td>
        <td>Active until</td>
    </tr>
    <#list transactions as transaction>
        <tr>
            <td>${transaction.id}</td>
            <td>${transaction.period}</td>
            <td>${transaction.type}</td>
            <td>${transaction.issueDate}</td>
            <td>${transaction.paidAt!}</td>
            <td>${transaction.activeSince!}</td>
            <td>${transaction.activeUntil!}</td>
        </tr>
    </#list>
</table>
<#if !account.familyToken??>
<p><a href="/admin/accounts/${account.id}/assignToken">Assign token</a></p>
<#else>
<p><a href="/admin/accounts/${account.id}/refresh">Refresh transactions</a></p>
<p><a href="/admin/accounts/${account.id}/unlink">Unlink</a></p>
</#if>
</@standardAdminPage>
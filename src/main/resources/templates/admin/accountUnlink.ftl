<#-- @ftlvariable name="token" type="ru.mray.core.model.FamilyToken" -->
<#-- @ftlvariable name="family" type="ru.mray.core.model.Family" -->
<#-- @ftlvariable name="account" type="ru.mray.core.model.Account" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#include 'library/standardAdminPage.ftl'>

<@standardAdminPage title="Unlink account">
<form method="POST" action="/admin/accounts/${account.id}/unlink">
    <table>
        <tr>
            <td>
                Account to unlink: <a href="/admin/accounts/${account.id}">${account.id}</a>
            </td>
        </tr>
        <tr>
            <td>
                Account email: ${account.email}
            </td>
        </tr>
        <tr>
            <td>
                Family login: ${family.login}
            </td>
        </tr>
        <tr>
            <td>
                Family password: ${family.password}
            </td>
        </tr>
        <tr>
            <td>
                Slot (0-based): ${token.slot}
            </td>
        </tr>
            <td>

            </td>
        </tr>
        <tr>
            <td>
                <label for="newToken">New family token</label>
                <input required name="newToken" id="newToken"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="button" id="submit">Выполнить</button>
            </td>
        </tr>
    </table>
</form>
</@standardAdminPage>
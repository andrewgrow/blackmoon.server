<#import "parts/common.ftl" as common>

<@common.page title="User Edit">

    <form action="/user" method="post">
        <p>${user.getUserId()}</p>
        <p><input type="text" name="username" value="${user.getUsername()}" /></p>
        <#list roles as role>
            <p><label><input type="checkbox" name="${role}" ${user.getRoles()?seq_contains(role)?string("checked", "")} />${role}</label></p>
        </#list>
        <p><input type="text" name="email" value="${user.getEmail()}" /></p>
        <p><input type="text" name="phone" value="${user.getPhone()}" /></p>
        <p><label><input type="password" name="password"/> New Password</label></p>
        <td><label><input type="checkbox" name="active" ${user.isActiveUser()?string("checked", "")}/>is active</label></td>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="user_id" value="${user.getUserId()}"/>
        <p><button type="submit">Save</button></p>
    </form>
</@common.page>
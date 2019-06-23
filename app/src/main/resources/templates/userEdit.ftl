<#import "parts/common.ftl" as common>

<@common.page title="User Edit">

    <form action="/user" method="post"></form>
    <p><input type="text" name="userId" value="${user.getUserId()?string}" readonly/></p>
    <p><input type="text" name="username" value="${user.getUsername()}" /></p>
    <#list roles as role>
        <p><label><input type="checkbox" name="${role}" ${user.getAuthorities()?seq_contains(role)?string("checked", "")} />${role}</label></p>
    </#list>
    <p><input type="text" name="email" value="${user.getEmail()}" /></p>
    <p><input type="text" name="phone" value="${user.getPhone()}" /></p>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <p><button type="submit">Save</button></p>
</@common.page>
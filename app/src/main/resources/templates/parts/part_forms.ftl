<#macro login_or_registration path>
    <#local submit = "Sign In">
    <#if path == "/registration">
        <#local submit = "Registration">
    </#if>

    <form action="${path}" method="post">
        <#if path == "/registration">
            <p>Registration form:</p>
            <#elseif path == "/login">
            <p>Login form:</p>
        </#if>

        <div><label> User Name : <input type="text" name="username" required placeholder="Your name"/> </label></div>
        <div><label> Password: <input type="password" name="password" required placeholder="Your password"/> </label></div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <#if path == "/registration">
            <div><label> Password: <input type="password" name="matching"
                                          required placeholder="Confirm your password"/> </label></div>
            <div><label> Email: <input type="email" name="email" required placeholder="Your email"/> </label></div>
            <div><label> Phone: <input type="text" name="phone" placeholder="Your phone"/> </label></div>
        </#if>
        <div><input type="submit" value="${submit}"/></div>
    </form>
</#macro>

<#macro log_out>
    <p>Logout:</p>
    <form action="/logout" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div> <input type="submit" value="Sign Out"/></div>
    </form>
</#macro>

<#macro update user>
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
</#macro>

<#macro delete user_id>
    <form action="/user/delete" method="post">
        <input type="hidden" name="user_id" value="${user_id}"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div> <input type="submit" value="Delete User"/></div>
    </form>
</#macro>
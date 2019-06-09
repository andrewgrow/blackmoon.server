<#macro login_or_registration path>

    <#local submit = "Sign In">
    <#if path == "/registration">
        <#local submit = "Registration">
    </#if>

    <form action="${path}" method="post">
        <div><label> User Name : <input type="text" name="username" required placeholder="Your name"/> </label></div>
        <div><label> Password: <input type="password" name="password" required placeholder="Your password"/> </label></div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <#if path == "/registration">
            <div><label> Password: <input type="password" name="password_confirm"
                                          required placeholder="Confirm your password"/> </label></div>
            <div><label> Email: <input type="email" name="email" required placeholder="Your email"/> </label></div>
            <div><label> Phone: <input type="text" name="phone" placeholder="Your phone"/> </label></div>
        </#if>
        <div><input type="submit" value="${submit}"/></div>
    </form>
</#macro>

<#macro log_out>
    <form action="/logout" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div> <input type="submit" value="Sign Out"/></div>
    </form>
</#macro>
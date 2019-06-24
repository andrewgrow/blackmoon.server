<#macro add_new_project>
    <div>
        <form method="POST" enctype="multipart/form-data" action="/projects">
            <table>
                <tr><td>Project name:</td><td><input type="text" name="name" placeholder="Project name" required/></td></tr>
                <tr><td>Short description:</td><td><input type="text" name="short" placeholder="Short description" /></td></tr>
                <tr><td>Long description:</td><td><input type="text" name="long" placeholder="Long description" /></td></tr>
                <tr><td>File to upload:</td><td><input type="file" name="file" multiple/></td></tr>
                <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</#macro>

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
            <div><label> Password: <input type="password" name="matching"
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
<#macro add_new_project>
    <div>
        <p>Add new project:</p>
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

<#macro update_project project>
    <form action="/projects/edit" method="post">
        <p>${project.getId()}</p>
        <p><input type="text" name="name" value="${project.getName()}" /></p>
        <p><input type="text" name="short_description" value="${project.getShortDescription()}" /></p>
        <p><input type="text" name="long_description" value="${project.getLongDescription()}" /></p>
        <#if project.getProjectFiles()??>
            <div>
                <table>
                    <tr>
                        <#list project.getProjectFiles() as image>
                            <td><img width="100px;" src="/img/${image.getPath()}" /></td>
                        </#list>
                    </tr>
                </table>
            </div>
        </#if>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="project_id" value="${project.getId()}"/>
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
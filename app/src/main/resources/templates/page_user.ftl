<#import "parts/part_common.ftl" as common>
<#import "parts/part_forms.ftl" as forms>

<@common.page title="User List">
    <#if users??>
        <table>
        <thead>
            <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Role</th>
                <th>Email</th>
                <th>Phone</th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user.getUserId()}</td>
                    <td>${user.getUsername()}</td>
                    <td><#list user.getRoles() as role>${role}<#sep>, </#list></td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getPhone()}</td>
                    <td><input type="checkbox" ${user.isActiveUser()?string("checked", "")} readonly /></td>
                    <td><a href="/user/${user.getUserId()}">Edit</a> </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <@forms.delete user_id="${user.getUserId()}" />
                    </td>
                </tr>
            </#list>
        </tbody>
        </table>
    </#if>
    <br /><br /><br />
    <a href="/projects">See Projects</a>
</@common.page>
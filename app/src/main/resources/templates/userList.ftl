<#import "parts/common.ftl" as common>

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
                    <td><#list user.getAuthorities() as role>${role}<#sep>, </#list></td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getPhone()}</td>
                    <td><a href="/user/${user.getUserId()}">Edit</a> </td>
                </tr>
            </#list>
        </tbody>
        </table>
    </#if>
</@common.page>
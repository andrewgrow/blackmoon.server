<#import "parts/part_common.ftl" as common>

<@common.page title="Black Moon Server Side">
    <#if user??>
        <p>Hello, ${user.getUsername()}!</p>
        <p>Click <a href="/projects">here</a> to see all projects</p>
        <br/><br/>
        <p>You can <a href="/logout">logout</a>, if you want. We will miss you!</p>
    <#else >
        <p>Hello, Friend! You have to login the site. Click here:</p>
        <p><a href="/login">ENTER</a></p>
    </#if>
</@common.page>
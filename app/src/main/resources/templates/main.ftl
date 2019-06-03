<#import "parts/common.ftl" as c>
<@c.page>
    <div>Hello, World! ${message}</div>

    <#if projects??>
        <#list projects as project>
            <p>${project.getProjectName()}</p>
        <#else>
            <div><!-- nothing to show yet --></div>
        </#list>
    </#if>

</@c.page>
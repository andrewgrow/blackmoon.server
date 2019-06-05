<#import "parts/common.ftl" as common>

<@common.page title = "Projects Page | Black Moon Server Side">
    <#if projects??>
        <#list projects as project>
            <p>${project.getProjectName()}</p>
        <#else>
            <div><!-- nothing to show yet --></div>
        </#list>
    </#if>

</@common.page>
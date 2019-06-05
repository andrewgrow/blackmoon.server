<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>

<@common.page title = "Projects Page | Black Moon Server Side">
    <div>
    <#if projects??>
        <#list projects as project>
            <p>${project.getProjectName()}</p>
        <#else>
            <div><!-- nothing to show yet --></div>
        </#list>
    </#if>
    </div>

    <div><p><@auth_form.log_out /></p></div>

</@common.page>
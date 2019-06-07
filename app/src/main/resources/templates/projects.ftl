<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>
<#import "parts/forms.ftl" as forms>

<@common.page title = "Projects Page | Black Moon Server Side">

    <div>
        <p>
            <@forms.add_new_project />
        </p>
    </div>

    <div>
        <p>
            <#if projects??>
            <#list projects as project>
                <p>${project.getName()}</p>
            <#else>
                <div><!-- nothing to show yet --></div>
            </#list>
            </#if>
        </p>
    </div>

    <br/><br/><br/><br/><br/>
    <div><p><@auth_form.log_out /></p></div>

</@common.page>
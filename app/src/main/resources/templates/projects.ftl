<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>
<#import "parts/forms.ftl" as forms>

<@common.page title = "Projects Page | Black Moon Server Side">


    <#if error??>
        <@common.show_error alert="${error}"  />
    </#if>

    <div>
        <p>
            <@forms.add_new_project />
        </p>
    </div>

    <div>
        <p>
            <#if projects??>
            <#list projects as project>
                <div><p>${project.getName()}</p></div>
                <div>
                    <#list project.getProjectFiles() as image>
                        <p>${image.getPath()}</p>
                        <p><img width="100px;" src="/img/${image.getPath()}" /></p>
                    </#list>
                </div>
            <#else>
                <div><!-- nothing to show yet --></div>
            </#list>
            </#if>
        </p>
    </div>

    <br/><br/><br/><br/><br/>
    <div><p><@auth_form.log_out /></p></div>

</@common.page>
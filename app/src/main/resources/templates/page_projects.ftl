<#import "parts/part_common.ftl" as common>
<#import "parts/part_forms.ftl" as forms>
<#import "parts/part_project.ftl" as part_project>

<@common.page title = "Projects Page | Black Moon Server Side">
    <div>
        <p>
            <@part_project.add_new_project />
        </p>
    </div>

    <div>
        <p>
            <#if projects??>
                <#list projects as project>
                    <@part_project.single_project project=project/>
                    <br/><br/>
                        <a href="/projects/${project.getId()}">Edit Project</a>
                    <br/><br/>
                <#else>
                    <div><!-- nothing to show yet --></div>
                </#list>
            </#if>
        </p>
    </div>

    <br/><br/><br/>
    <#if user??>
        <#if user.isAdmin()>
            <a href="/user">See Users List</a>
            <br/><br/><br/>
        </#if>
    </#if>
    <div><p><@forms.log_out /></p></div>

</@common.page>
<#import "parts/part_common.ftl" as common>
<#import "parts/part_forms.ftl" as forms>
<#import "parts/part_project.ftl" as part_project>

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
                    <@part_project.single_project project=project/> <a href="/projects/${project.getId()}">Edit</a>
                    <br/><br/>
                <#else>
                    <div><!-- nothing to show yet --></div>
                </#list>
            </#if>
        </p>
    </div>

    <br/><br/><br/><br/><br/>
    <div><p><@forms.log_out /></p></div>

</@common.page>
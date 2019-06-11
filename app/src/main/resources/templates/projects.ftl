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
                    <div><p>Project name: ${project.getName()}</p></div>
                    <div><p>Short description: ${project.getShortDescription()}</p></div>
                    <div><p>Long description: ${project.getLongDescription()}</p></div>
                    <#if project.getProjectFiles()??>
                        <div>
                            <table>
                                <tr>
                                    <#list project.getProjectFiles() as image>
                                        <td><img width="100px;" src="/img/${image.getPath()}" /></td>
                                    </#list>
                                </tr>
                            </table>
                        </div>
                    </#if>
                    <br/><br/>
                <#else>
                    <div><!-- nothing to show yet --></div>
                </#list>
            </#if>
        </p>
    </div>

    <br/><br/><br/><br/><br/>
    <div><p><@auth_form.log_out /></p></div>

</@common.page>
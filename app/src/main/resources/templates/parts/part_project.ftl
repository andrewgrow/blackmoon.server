<#macro single_project project>
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
</#macro>
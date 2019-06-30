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

<#macro add_new_project>
    <div>
        <p>Add new project:</p>
        <form method="POST" enctype="multipart/form-data" action="/projects">
            <table>
                <tr><td>Project name:</td><td><input type="text" name="name" placeholder="Project name" required/></td></tr>
                <tr><td>Short description:</td><td><input type="text" name="short" placeholder="Short description" /></td></tr>
                <tr><td>Long description:</td><td><input type="text" name="long" placeholder="Long description" /></td></tr>
                <tr><td>File to upload:</td><td><input type="file" name="file" multiple/></td></tr>
                <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</#macro>

<#macro update_project project>
    <form action="/projects/edit" method="post">
        <p>${project.getId()}</p>
        <p><input type="text" name="name" value="${project.getName()}" /></p>
        <p><input type="text" name="short_description" value="${project.getShortDescription()}" /></p>
        <p><input type="text" name="long_description" value="${project.getLongDescription()}" /></p>
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
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="project_id" value="${project.getId()}"/>
        <p><button type="submit">Save</button></p>
    </form>
</#macro>
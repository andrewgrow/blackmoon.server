<#macro single_project project>
    <div><p>Project name: ${project.getName()}</p></div>
    <div><p>Short description: ${project.getShortDescription()}</p></div>
    <div><p>Long description: ${project.getLongDescription()}</p></div>
    <#if project.getProjectFiles()??>
        <div>
            <table>
                <tr>
                    <#list project.getProjectFiles() as image>
                        <td>
                            <img width="100px;" src="${image.getPath()}" />
                        </td>
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
    <p>You can remove whole project:</p>
    <form action="/projects/delete" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="project_id" value="${project.getId()}"/>
        <p><button type="submit">DELETE PROJECT</button> DANGER! It will remove all data!</p>
    </form>
    <br /><br /><br /><br />


    <p>You can remove files from the project:</p>
    <#if project.getProjectFiles()??>
        <div>
            <table>
                <tr>
                    <#list project.getProjectFiles() as image>
                        <td>
                            <img width="100px;" src="${image.getPath()}" />
                            <br/>
                            <form action="/projects/delete_photo" method="post">
                                <input type="hidden" name="project_id" value="${project.getId()}" />
                                <input type="hidden" name="photo_id" value="${image.getId()}" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit">Delete Photo</button>
                            </form>
                        </td>
                    </#list>
                </tr>
            </table>
        </div>
    </#if>
    <br /><br /><br />

    <p>You can just update the project:</p>
    <form action="/projects/edit" enctype="multipart/form-data" method="post">
        <p>Project ID: ${project.getId()}</p>
        <p><label>Project Name: <input type="text" name="name" value="${project.getName()}" /></label></p>
        <p><label>Short Description: <input type="text" name="short_description" value="${project.getShortDescription()}" /></label></p>
        <p><label>Long Description: <input type="text" name="long_description" value="${project.getLongDescription()}" /></label></p>
        <p>Add new files to the project: <input type="file" name="file" multiple/></p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="project_id" value="${project.getId()}"/>
        <p><button type="submit">Save</button></p>
    </form>
</#macro>
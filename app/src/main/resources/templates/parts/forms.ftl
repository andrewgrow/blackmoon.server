<#macro add_new_project>
    <div>
        <form method="POST" enctype="multipart/form-data" action="/projects">
            <table>
                <tr><td>Project name:</td><td><input type="text" name="name" placeholder="Project name" /></td></tr>
                <tr><td>Short description:</td><td><input type="text" name="short" placeholder="Short description" /></td></tr>
                <tr><td>Long description:</td><td><input type="text" name="long" placeholder="Long description" /></td></tr>
                <tr><td>File to upload:</td><td><input type="file" name="file" multiple/></td></tr>
                <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</#macro>
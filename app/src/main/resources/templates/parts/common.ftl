<#macro page title>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>${title?html}</title>
    </head>

    <body>
        <#nested>
    </body>
    </html>
</#macro>

<#macro show_error alert>
    <div>
        <p>
            Error: ${alert}
        </p>
    </div>
</#macro>
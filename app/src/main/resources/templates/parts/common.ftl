<#macro page title>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>${title?html}</title>
        <link rel="stylesheet" href="/static/style.css" />
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
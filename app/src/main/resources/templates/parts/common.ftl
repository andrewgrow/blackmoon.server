<#macro page title>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>${title?html}</title>
        <link rel="stylesheet" href="/static/style.css" />

        <script src="https://browser.sentry-cdn.com/5.4.0/bundle.min.js" crossorigin="anonymous">
        </script>
        <script>
            Sentry.init({
                dsn: 'https://bcf086581e12459cbeab4bffebb3b783@sentry.io/1483571'
            });
        </script>
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
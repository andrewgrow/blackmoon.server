<#macro messages>
    <#if info_message??>
        <div><p>${info_message}</p></div>
    </#if>
    <#if error_message??>
        <div><p>${error_message}</p></div>
    </#if>
    <#if RequestParameters.error_message??>
        <div><p>${RequestParameters.error_message}</p></div>
    </#if>
    <#if RequestParameters.info_message??>
        <div><p>${RequestParameters.info_message}</p></div>
    </#if>
</#macro>
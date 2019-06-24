<#macro messages>
    <#if info_message??>
        <div><p>${info_message}</p></div>
    <#else>
        <div><p>You have to login or registration.</p></div>
    </#if>
</#macro>
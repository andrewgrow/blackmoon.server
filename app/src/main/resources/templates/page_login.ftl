<#import "parts/part_common.ftl" as common>
<#import "parts/part_forms.ftl" as forms>

<@common.page title="Login Page | Black moon Server Side">

    <#if RequestParameters.logout??>
        <div><p>You have been logged out. Want to login again?</p></div>

    <#elseif RequestParameters.error??>
        <#if RequestParameters.error_message??>
            <div><p>${RequestParameters.error_message}</p></div>
        <#else>
            <div><p>Invalid username and password.</p></div>
        </#if>
    <#else>
    </#if>

    <div><p><@forms.login_or_registration path="/login" /></div></p>
    <br/><br/><br/>
    <div><p><a href="/registration">REGISTRATION</div></p>

</@common.page>
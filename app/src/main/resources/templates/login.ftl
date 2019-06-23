<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>

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
        <#if message??>
            <div><p>${message}</p></div>
        <#else>
            <div><p>You have to login or registration.</p></div>
        </#if>
    </#if>

    <div><p><@auth_form.login_or_registration path="/login" /></div></p>
    <br/><br/><br/>
    <div><p><a href="/registration">REGISTRATION</div></p>

</@common.page>
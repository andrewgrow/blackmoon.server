<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>

<@common.page title="Login Page | Black moon Server Side">

    <#if RequestParameters.logout??>
        <div><p>You have been logged out.</p></div>
    <#elseif RequestParameters.error??>
        <div><p>Invalid username and password.</p></div>
    </#if>
    <div><p><@auth_form.login_or_registration path="/login" /></div></p>
</@common.page>
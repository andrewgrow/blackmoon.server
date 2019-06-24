<#import "parts/part_common.ftl" as common>
<#import "parts/part_forms.ftl" as forms>

<@common.page title="Login Page | Black moon Server Side">

    <#if RequestParameters.logout??>
        <div><p>You have been logged out. Want to login again?</p></div>
    </#if>

    <div><p><@forms.login_or_registration path="/login" /></div></p>
    <br/><br/><br/>
    <div><p><a href="/registration">REGISTRATION</div></p>

</@common.page>
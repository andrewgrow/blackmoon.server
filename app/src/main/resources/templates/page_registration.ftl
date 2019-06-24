<#import "parts/part_common.ftl" as common>
<#import "parts/part_forms.ftl" as forms>

<@common.page title="Registration Page | Black moon Server Side">

    <#if error??>
        <div><p>${error}</p> Want to login?</div>
        <div><p><@forms.login_or_registration path="/login" /></p></div>
        <div><p>or register new user:</p></div>
    </#if>
    <div><p><@forms.login_or_registration path="/registration" /></p></div>
</@common.page>
<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>

<@common.page title="Registration Page | Black moon Server Side">

    <#if message??>
        <div><p>${message}</p></div>
    </#if>
    <div><p><@auth_form.login_or_registration path="/registration" /></div></p>
</@common.page>
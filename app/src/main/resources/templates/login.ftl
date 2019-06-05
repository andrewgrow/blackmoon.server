<#import "parts/common.ftl" as common>
<#import "parts/auth_forms.ftl" as auth_form>

<@common.page title="Login Page | Black moon Server Side">
    <@auth_form.login_or_registration path="/login" />
</@common.page>
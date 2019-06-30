<#import "parts/part_common.ftl" as common>
<#import "parts/part_project.ftl" as part_project>

<@common.page title="Edit Project">
    <@part_project.update_project project=project />
</@common.page>
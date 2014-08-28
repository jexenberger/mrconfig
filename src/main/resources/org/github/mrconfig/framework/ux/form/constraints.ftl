<#if field.readOnly>
    readonly="true"</#if>
<#if !field.readOnly>
    <#list field.constraints as constraint>
        <#if capture>
            <#if constraint.elementDirective || constraint.both>
    ${constraint.id}
             </#if>
            <#if constraint.attribute || constraint.both>
               <#list constraint.attributes as attr>
    ng-${attr.car}="${attr.cdr}"
               </#list>
            </#if>
        </#if>
    </#list>
</#if>

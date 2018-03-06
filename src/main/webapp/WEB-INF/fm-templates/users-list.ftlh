<!DOCTYPE HTML>
<html lang="pl">
<head>
    <title>
        Users list
    </title>
</head>
<body>
<#list users>
<#items as user>
    ID:${user.id}<br>
    Name:${user.name}<br>
    Login:${user.login}<br>
    Age:${user.age}<br>
    Gender:${user.gender}<br>
    <#if (user.imageURL) ??>
<img src="${user.imageURL}" width="200"/>
    </#if>
    <#sep>*********************************************** </#sep>
</#items>
</#list>
</body>
</html>
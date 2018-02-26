<!DOCTYPE html>
<html>
<head>
    <title>Find user by ID</title>
</head>
<body>
<#list users>
    <#items as user>
        <p>ID: ${user.id}</p>
        <p>name: ${user.name}</p>
        <p>login: ${user.login}</p>
        <p>age: ${user.age}</p>
    </#items>
    <#sep> <p>========================================</p> </#sep>
</#list>
</body>
</html>
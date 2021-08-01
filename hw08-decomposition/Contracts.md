## Описание контраковы взаимодействия сервисов друг с другом.

### ms-user
- Создание пользователя
> Rest-API. </br>
> POST. Content-Type: application/json; </br>
> URL: */user/ </br>
> Example request:
`
{
"userId":325,
"orderName":"4343",
"idempotencyKey": "e3fb3ed1-4de1-4394-a180-e7d684d815be"
}
`
> 
- Обновление пользователя
> Rest-API. </br>
> PUT. Content-Type: application/json; </br>
> URL: */user/ </br>
> Example request:
`
{
"id":3,
"username":"updatedUserName",
"firstName":"updatedFirstName",
"lastName": "updatedLastName",
"email":"updatedemail@email.email",
"phone":"updated+4434",
"idempotencyKey": "e3fb3ed1-4de1-4394-a180-e7d684d815be"
}
`
>
- Удаление пользователя
> Rest-API. </br>
> POST </br>
> URL: */user/{userId}
>

### ms-auth
- Логин
> Rest-API. </br>
> POST. Content-Type: application/json; </br>
> URL: */auth/login </br>
> Example request:
`
{
"username":"admin",
"firstName":"FirstName",
"lastName": "LastName",
"email":"email@email.email",
"phone":"+4434",
"password": "admin"
}
`
>
- Авторизация
> Rest-API. </br>
> POST </br>
> URL: */auth/auth
>

- Регистрация
> Rest-API. </br>
> POST. Content-Type: application/json; </br>
> URL: */auth/registration </br>
> Example request:
`
{
"username":"admin",
"firstName":"FirstName",
"lastName": "LastName",
"email":"email@email.email",
"phone":"+4434",
"password": "admin"
}
`
- Логаут
> Rest-API. </br>
> GET </br>
> URL: */auth/logout
>

### ms-order
- Создание заказа
> Rest-API. </br>
> POST. Content-Type: application/json; </br>
> URL: */order </br>
> Example request:
`
{
"userId":325,
"orderName":"4343",
"idempotencyKey": "e3fb3ed1-4de1-4394-a180-e7d684d815be"
}
`

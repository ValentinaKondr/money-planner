# Обзор проекта: Приложение для управления бюджетом

---

### Описание

Приложение функционирует через отправку API-запросов на указанные эндпоинты контроллера:  
**[BudgetController.java](src/main/java/v/budget/api/BudgetController.java)**

---

## Функционал приложения

**Эндпоинты**:

1. **POST** `api/v1/budget/register`
    - **Назначение**: Регистрация пользователя
    - **Пример запроса**:
      ```json
      {
        "login": "123",
        "password": "123"
      }
      ```
    - **Пример ответа**:
      ```json
      {
        "id": 1,
        "login": "123",
        "password": "123"
      }
      ```

2. **POST** `api/v1/budget/category`
   - **Назначение**: Добавление категории. Присутствует проверка пользователя по userId, кидается 404 NOT FOUND
   - **Пример запроса**:
     ```json
     {
        "userId": 1,
        "name": "FOOD",
        "limit": 1000
     }
     ```
   - **Пример ответа**:
     ```json
     {
        "id": 3,
        "userId": 1,
        "name": "FOOD",
        "limit": 1000
     }
     ```
     
3. **POST** `api/v1/budget/operation`
    - **Назначение**: Добавление операции.  
      Сперва происходит проверка наличия пользователя, потом - категории. Если не найдено, кидается 404 NOT FOUND
    - **Пример запроса**:
      ```json
      {
        "userId": 1,
        "categoryName": "SALARY",
        "amount": 200000.0,
        "type": "INCOME"
      }
      ```
      ```
    - **Пример ответа**:
      ```json
       {
          "id": 3,
          "userId": 1,
          "categoryName": "SALARY",
          "amount": 200000.0,
          "type": "INCOME"
      }
      ```
4. **GET** `api/v1/budget?={userId}`
    - **Назначение**: Этот запрос возвращает сводку бюджета со всеми категориями и суммарными расчетами
    - **Пример ответа**:
      ```json
      {
        "totalIncome": 400000.0,
        "totalExpense": 200.0,
        "incomes": [
          {
            "name": "DIVIDENTS",
            "amount": 200000.0
          },
          {
            "name": "SALARY",
            "amount": 200000.0
          }
        ],
        "expenses": [
          {
            "name": "FOOD",
            "limit": 100.0,
            "amount": 200.0
          }
        ]
       }
      ```


* [AdviceController.java](src/main/java/v/budget/api/AdviceController.java)f

Возвращает ошибку, если не найден ресурс(пользователь или категория), или другие ошибки.
Для каждого такого случая добавлено логирование, особенно если с новой операцией превышается лимит

### Модель данных
Исходное состояние БД задается с помощью скриптов liquibase [db.changelog-master.xml](src/main/resources/db/changelog/init)
Возможность работы нескольких пользователей и создания категорий и операций на каждого пользователя
достигается соответствующими ограничениями для каждой таблицы

### Конфигурационный файл [application.properties](src/main/resources/application.properties)
```properties
# настройки приложения
spring.application.name=budget
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
spring.liquibase.default-schema=public
## Как тестировать приложение
Я тестировала с помощью Postman. Необходимо в настройках - [application.properties](src/main/resources/application.properties)
указать данные подключения к БД

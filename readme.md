Based on Spring Boot, SQL - PostgreSQL.
Визуализация не требовалась, но и не была запрещена, поэтому это web-приложение.
Перед финальной сборкой изменить параметры почты отправителя в avito/testingAvito/service/mail/MailConfig.java

+ Проверка валидности эл. почты

+ Проверка занято/свободно время участников (выдавать предупреждение)
    При попытке записи участника на встречу при том, что у него этот день занят, будет выведено сообщение,
     и запись новой встречи заменит запись старой.
     
+- Отправить приглашение на встречу всем участникам в виде емэйл
    Письма отправляет, но потом падает(
За 15 минут до встречи отправить напоминание(не реализовано)

Также написан тестовый сценарий для добавления участников в встречу

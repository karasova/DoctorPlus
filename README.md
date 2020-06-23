# DoctorPlus

Android Marshmallow 6.0

App name DoctorPlus

## Activities:

* EnterActivity — вход в приложение (Антон)
* StartActivity — главный экран после входа (?Лиза + Антон?)
* OpenCallsActivity — экран с доступными вызовами (Лиза)
* ClosedCallsActivity — прошлые вызовы (Лиза)
* ActiveCallActivity — активный вызов (Юля)
* CardActivity — карточка пациента (Юля)
* ProblemsActivity — сообщить о проблеме (Юля, опционально)
* ProfileActivity — профиль (Антон)

## Постоянные переменные приложения

Установ очка переменной для ID пользователя, ей будут пользоваться все, так что следим за названием переменной!!!
```
SharedPreferences preferences;
preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);

```
Чтобы поместить переменную 

```

preferences.edit().putString("user_id", USER_ID).apply();

```
Чтобы достать ее

```

String user_id = preferences.getString("user_id", "-1");

```
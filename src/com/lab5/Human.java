package com.lab5;


import com.lab5.Exceptions.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.lab5.Exceptions.*;

/**
 * Класс человека
 */
public class Human {

  /**
   * Для приведения чисел к нужному формату
   */
  private final DecimalFormat decimalFormat = new DecimalFormat("#.###");
  /**
   * День рождения человека
   */
  private int bDay;
  /**
   * Месяц рождения человека
   */
  private int bMonth;
  /**
   * Год рождения человека
   */
  private int bYear;
  /**
   * Рост человека
   */
  private float height;
  /**
   * Вес человека
   */
  private float weight;
  /**
   * Имя человека
   */
  private String name;
  /**
   * Фамилия человека
   */
  private String surname;
  /**
   * Пол человека
   */
  private boolean gender;
  /**
   * Флаг, активируемый при вводе пола в экземпляр класса
   */
  private boolean isGender;

  /**
   * Конструктор возвращающий пустой экземпляр класса
   */
  public Human() {

    this.isGender = false;
  }

  /**
   * Конструктор возвращаюший заполненный экземпляр класса
   *
   * @param bDay    День рождения
   * @param bMonth  Месяц рождения
   * @param bYear   Год рождения
   * @param height  Рост человека
   * @param weight  Вес человека
   * @param name    Имя человека
   * @param surname Фамилия человека
   * @param gender  Пол человека
   * @throws IncorrectStringException при некорректных строковых значениях
   * @throws IncorrectFloatException  при некорректных float значениях
   * @throws IncorrectIntException    при некорректных int значениях
   */
  public Human(int bDay, int bMonth, int bYear,
      float height, float weight,
      String name, String surname, String gender)
      throws IncorrectStringException, IncorrectIntException, IncorrectFloatException {

    if (bDay <= 0) {
      throw new IncorrectIntException("Некорректный день в дате дня рождения", bDay);
    }
    if (bMonth <= 0) {
      throw new IncorrectIntException("Некорректный месяц в дате дня рождения", bMonth);
    }
    if (bYear <= 0) {
      throw new IncorrectIntException("Некорректный год в дате дня рождения", bYear);
    }
    if (height <= 0) {
      throw new IncorrectFloatException("Некорректный рост человека", height);
    }
    if (weight <= 0) {
      throw new IncorrectFloatException("Некорректный вес человека", weight);
    }
    if (name == null) {
      throw new IncorrectStringException("Некорректное имя человека", null);
    }
    if (surname == null) {
      throw new IncorrectStringException("Некорректная фамилия человека", null);
    }
    this.bDay = bDay;
    this.bMonth = bMonth;
    this.bYear = bYear;

    this.height = height;
    this.weight = weight;

    this.name = name;
    this.surname = surname;
    setGender(gender);
    this.isGender = true;
  }


  /**
   * Метод установки даты рождения
   *
   * @param date дата в формте ДД.ММ.ГГГГ
   * @throws IncorrectStringException При некорректном формате даты
   */
  public void setBirthday(String date) throws IncorrectStringException {
    int[] values = new int[3];
    String pattern = "(0[1-9]|[12]\\d|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
    Pattern pat = Pattern.compile(pattern);
    Matcher match = pat.matcher(date);

    if (!match.find()) {
      throw new IncorrectStringException("Неверный формат даты рождения", date);
    }
    String[] dateList = date.split("\\.");
    for (int i = 0; i < dateList.length; i++) {
      values[i] = Integer.parseInt(dateList[i]);
    }
    bDay = values[0];
    bMonth = values[1];
    bYear = values[2];
  }

  /**
   * Метод получения строкового представления даты рождения
   *
   * @return Дату рождения в формате ДД.ММ.ГГГГ
   */
  public String getBirthday() {
    return String.format("%02d.%02d.%d", this.bDay, this.bMonth, this.bYear);
  }

  /**
   * Метод получения имя и фамилии человека
   *
   * @return имя и фамилия через пробел
   */
  public String getFullName() {
    return String.format("%s %s", this.name, this.surname);
  }

  /**
   * Сеттер веса человека
   *
   * @param weight вес человека
   * @throws IncorrectFloatException при некорректном весе человека
   */
  public void setWeight(float weight) throws IncorrectFloatException{
    if (weight <= 0) {
      throw new IncorrectFloatException("Некорректный вес", weight);
    }
    this.weight = weight;
  }

  /**
   * Геттер веса человека
   *
   * @return вес человека
   */
  public float getWeight() {
    return this.weight;
  }

  /**
   * Сеттер роста человека
   *
   * @param height рост человека
   * @throws IncorrectFloatException при некорректном росте человека
   */
  public void setHeight(float height) throws IncorrectFloatException{
    if (height <= 0) {
      throw new IncorrectFloatException("Некорректный вес ", weight);
    }
    this.height = height;
  }

  /**
   * Геттер роста человека
   *
   * @return рост человека
   */
  public float getHeight() {

    return this.height;
  }

  /**
   * Метод получения даты рожденгия для компаратора
   *
   * @return Экземпляр Date содержащий дату рождения
   */
  public Date getBDate() {
    return new Date(this.bYear, this.bMonth, this.bDay);
  }

  /**
   * Сеттер имени человека
   *
   * @param name имя человека
   * @throws IncorrectStringException при некорректном имени человека
   */
  public void setName(String name) throws IncorrectStringException {
    if (name == null) {
      throw new IncorrectStringException("Некорректное имя человека", null);
    }
    this.name = name;
  }

  /**
   * Геттер имени человека
   *
   * @return Имя человека
   */
  public String getName() {
    return this.name;
  }

  /**
   * Сеттер фамилии человека
   *
   * @param surname фамилия человека
   * @throws IncorrectStringException при некорректной фамилии человека
   */
  public void setSurname(String surname) throws IncorrectStringException {
    if (surname == null) {
      throw new IncorrectStringException("Некорректная фамилия человека", null);
    }
    this.surname = surname;
  }

  /**
   * Геттер фамилии человека
   *
   * @return Фамилию человека
   */
  public String getSurname() {
    return this.surname;
  }

  /**
   * Сеттер пола человека, преобразует строковые обозначения в boolean
   *
   * @param gender, где ж - женский пол, м - мужской
   * @throws IncorrectStringException при некорректном обозначении пола (м/ж)
   */
  public void setGender(String gender) throws IncorrectStringException{
    if (Objects.equals(gender.toLowerCase(), "м")) {
      this.gender = true;
      this.isGender = true;
    } else if (Objects.equals(gender.toLowerCase(), "ж")) {
      this.gender = false;
      this.isGender = true;
    } else {
      throw new IncorrectStringException("Некорректное обозначение пола", gender);
    }
  }

  /**
   * Геттер пола человека
   *
   * @return Строку содержащую пол человека
   */
  public String getGender() {
    if (!this.isGender) {
      return "Нет данных";
    }
    if (this.gender) {
      return "Мужской";
    } else {
      return "Женский";
    }
  }

  /**
   * Метод получения ИМТ человека, рассчитываемый из роста и веса человека
   *
   * @return строку, содержащую вердикт о комплекции человека согласно ИМТ
   */
  public String getBMI() {
    float VERY_LOW_MASS = 16f,
        LOW_MASS = 18.5f,
        NORM_MASS = 25f,
        NEAR_TO_FAT_MASS = 30f,
        FAT_MASS = 35f,
        VERY_FAT_MASS = 40f;
    if (this.weight == 0 || this.height == 0) {
      return "Недостаточно данных";
    }
    double BMI = this.weight / Math.pow(this.height, 2);
    if (BMI <= VERY_LOW_MASS) {
      return "Выраженный дефицит массы тела";
    } else if (BMI < LOW_MASS) {
      return "Недостаточная масса тела";
    } else if (BMI < NORM_MASS) {
      return "Норма";
    } else if (BMI < NEAR_TO_FAT_MASS) {
      return "Избыточная масса тела";
    } else if (BMI < FAT_MASS) {
      return "Ожирение 1 степени";
    } else if (BMI < VERY_FAT_MASS) {
      return "Ожирение 2 степени";
    } else {
      return "Ожирение 3 степени";
    }
  }

  /**
   * Метод строкового представления класса
   */
  @Override
  public String toString() {
    return String.format("""
            Имя: %s
            Фамилия: %s
            Дата рождения: %s
            Пол: %s
            Рост: %s
            Вес: %s
            Конфигурация тела,
            согласно ИМТ: %s""",
        this.getName(),
        this.getSurname(),
        this.getBirthday(),
        this.getGender(),
        this.decimalFormat.format(this.getHeight()),
        this.decimalFormat.format(this.getWeight()),
        this.getBMI());
  }
}
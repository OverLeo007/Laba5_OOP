package com.lab5;

import com.lab5.Exceptions.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс для запуска программы
 */
public class Laba {


  public static void main(String[] args) {

    new UI().menu();
  }

}

/**
 * Интерфейс, реализующий нумерацию вариантов выбора для switch()
 */
interface menuEnum {

  int PRINT_HUMANS = 1,
      ADD_VOID_HUMAN = 2,
      ADD_FILLED_HUMAN = 3,
      DELETE_HUMAN = 4,
      EDIT_HUMAN = 5,
      SORT_HUMANS = 6,
      EXIT = 7;

  int CHANGE_NAME = 1,
      CHANGE_SURNAME = 2,
      CHANGE_BIRTHDAY = 3,
      CHANGE_GENDER = 4,
      CHANGE_HEIGHT = 5,
      CHANGE_WEIGHT = 6,
      EXIT_TO_MENU = 7;

  int SORT_BY_BIRTHDAY = 1,
      SORT_BY_HEIGHT = 2,
      SORT_BY_WEIGHT = 3,
      SORT_BY_NAME = 4,
      SORT_BY_SURNAME = 5,
      SORT_BY_FULL_NAME = 6,
      SORT_BY_GENDER = 7;
}

/**
 * Основной класс интерфейса
 */
class UI implements menuEnum {


  /**
   * Поток вывода, поддерживающий русские символы
   */
  private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

  /**
   * Экземпляр класса содержащего методы ввода разных типов
   */
  private final Inputer inp = new Inputer();

  /**
   * Основной список людей
   */
  private final ArrayList<Human> humans = new ArrayList<>();


  /**
   * Метод основного меню работы
   */
  public void menu() {
//    try {
//      this.humans.add(new Human(25, 8, 2003, 1.85f, 71, "Leo", "Sokolov", "м"));
//      this.humans.add(new Human(31, 10, 2008, 1.65f, 51, "Katya", "Sokolova", "ж"));
//      this.humans.add(new Human(19, 12, 2003, 1.76f, 56, "Arisha", "Demekhina", "ж"));
//    } catch (Exception e) {
//      out.println("???");
//    }

    int choice;
    do {
      out.println("""
          1. Вывести список людей
          2. Добавить незаполненного человека к списку
          3. Добавить человека, заполняя данные
          4. Удалить человека по индексу
          5. Отредактировать данные человека по индексу
          6. Отсортировать список людей
          7. Выйти из программы
          """);

      choice = inp.getInt();
      try {
        switch (choice) {
          case PRINT_HUMANS -> printHumans();
          case ADD_VOID_HUMAN -> addVoidHuman();
          case ADD_FILLED_HUMAN -> addParamHuman();
          case DELETE_HUMAN -> deleteHuman();
          case EDIT_HUMAN -> editHuman();
          case SORT_HUMANS -> sortHumans();
          default -> {
            if (choice != EXIT) {
              out.println("Некорректный ввод");
            }
          }

        }
      } catch (IncorrectStringException |
               IncorrectFloatException |
               IncorrectIntException |
               HumanCreateException exception) {
        out.println("При вводе какого-то значения произошла ошибка: "
            + exception.getMessage());
      } catch (AssertionError e) {
        out.println(e.getMessage());
      }
    } while (choice != EXIT);
  }

  /**
   * Метод, реализующий сортировку по выбранному в нем полю
   */
  private void sortHumans() {
    out.println("""
        1. Сортировка по дате рождения
        2. Сортировка по росту
        3. Сортировка по весу
        4. Сортировка по Имени
        5. Сортировка по фамилии
        6. Сортировка по имени и фамилии
        7. Сортировка по полу
        """);
    int choice = inp.getInt();
    switch (choice) {
      case SORT_BY_BIRTHDAY -> humans.sort(Comparator.comparing(Human::getBDate).reversed());
      case SORT_BY_HEIGHT -> humans.sort(Comparator.comparing(Human::getHeight));
      case SORT_BY_WEIGHT -> humans.sort(Comparator.comparing(Human::getWeight));
      case SORT_BY_NAME -> humans.sort(Comparator.comparing(Human::getName));
      case SORT_BY_SURNAME -> humans.sort(Comparator.comparing(Human::getSurname));
      case SORT_BY_FULL_NAME -> humans.sort(Comparator.comparing(Human::getFullName));
      case SORT_BY_GENDER -> humans.sort(Comparator.comparing(Human::getGender));
      default -> System.out.println("Неизвестный параметр");
    }

  }

  /**
   * Метод удаления человека из списка по индексу
   *
   * @throws IncorrectIntException при некорректном индексе человека в списке
   * @throws AssertionError        при пустом списке
   */
  private void deleteHuman() throws IncorrectIntException, AssertionError {
    assert !humans.isEmpty() : "Не добавлено ни одного человека";
    out.println("Введите индекс человека в списке:");
    int index = inp.getInt();
    if (index < 1 || index > humans.size()) {
      throw new IncorrectIntException("Некорректный индекс человека в списке", index);
    } else {
      out.println(humans.get(index - 1).getFullName() + " удален из списка");
      humans.remove(index - 1);
    }
  }

  /**
   * @param date дата в формате ДД.ММ.ГГГГ
   * @return массив вида [День, Месяц, Год]
   * @throws IncorrectStringException при некорректном написании даты
   */
  public int[] getBirthdayValues(String date) throws IncorrectStringException {
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
    return values;
  }

  /**
   * Метод добавления человека с задаваемыми параметрами
   *
   * @throws IncorrectStringException при некорректном заполнении значений строкового типа
   * @throws HumanCreateException     при некорректном заполнении значений типа int
   */
  private void addParamHuman()
      throws IncorrectStringException, HumanCreateException {

    int[] birthday;
    float height, weight;
    String name, surname, gender;

    out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
    birthday = getBirthdayValues(inp.getString());

    out.println("Введите рост в метрах:");
    height = inp.getFloat();

    out.println("Введите вес в килограммах:");
    weight = inp.getFloat();

    out.println("Введите имя:");
    name = inp.getString();

    out.println("Введите фамилию:");
    surname = inp.getString();

    out.println("Введите пол (м/ж):");
    gender = inp.getString();

    try {
      humans.add(new Human(birthday[0], birthday[1], birthday[2],
          height, weight, name, surname, gender));
    } catch (IncorrectIntException | IncorrectFloatException | IncorrectStringException e) {
      throw new HumanCreateException(
          "Была произведена ошибка при заполнении полей", e);
    }
  }

  /**
   * Метод добавления человека с незаполненными полями
   */
  private void addVoidHuman() {
    humans.add(new Human());
  }


  /**
   * Метод редактирования любых полей человека
   *
   * @throws IncorrectIntException    при некорректном заполнении значений типа int
   * @throws IncorrectFloatException  при некорректном заполнении значений типа float
   * @throws IncorrectStringException при некорректном заполнении значений строкового типа
   * @throws AssertionError           при пустом списке
   */
  private void editHuman() throws IncorrectIntException,
      IncorrectFloatException, IncorrectStringException, AssertionError {
    assert !humans.isEmpty() : "Не добавлено ни одного человека";
    out.println("Введите индекс человека в списке:");
    int index = inp.getInt();
    if (index < 1 || index > humans.size()) {
      throw new IncorrectIntException("Некорректный индекс человека в списке", index);
    }
    Human humanToEdit = this.humans.get(index - 1);
    int choice;
    do {
      out.println("""
          1. Изменить имя
          2. Изменить фамилию
          3. Изменить дату рождения
          4. Изменить пол
          5. Изменить рост
          6. Изменить вес
          7. Выйти в меню
          """);
      choice = inp.getInt();
      switch (choice) {

        case CHANGE_NAME -> changeNameHandler(humanToEdit);
        case CHANGE_SURNAME -> changeSurnameHandler(humanToEdit);
        case CHANGE_BIRTHDAY -> changeBirthdayHandler(humanToEdit);
        case CHANGE_GENDER -> changeGenderHandler(humanToEdit);
        case CHANGE_HEIGHT -> changeHeightHandler(humanToEdit);

        case CHANGE_WEIGHT -> changeWeightHandler(humanToEdit);
        case EXIT_TO_MENU -> out.println("Редактирование завершено");
        default -> out.println("Некорректный ввод!");
      }

    } while (choice != EXIT_TO_MENU);
  }

  /**
   * Метод изменяющий имя выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   * @throws IncorrectStringException при некорректном вводе имени
   */
  private void changeNameHandler(Human humanToEdit) throws IncorrectStringException {
    out.println("Введите имя:");
    humanToEdit.setName(inp.getString());
  }

  /**
   * Метод изменяющий фамилию выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   * @throws IncorrectStringException при некорректном вводе фамилии
   */
  private void changeSurnameHandler(Human humanToEdit) throws IncorrectStringException {
    out.println("Введите фамилию:");
    humanToEdit.setSurname(inp.getString());
  }

  /**
   * Метод изменяющий день рождения выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   * @throws IncorrectStringException при некорректном вводе даты рождения
   */
  private void changeBirthdayHandler(Human humanToEdit) throws IncorrectStringException {
    out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
    humanToEdit.setBirthday(inp.getString());
  }

  /**
   * Метод изменяющий пол выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   * @throws IncorrectStringException при некорректном вводе пола человека
   */
  private void changeGenderHandler(Human humanToEdit) throws IncorrectStringException {
    out.println("Введите пол (м/ж):");
    humanToEdit.setGender(inp.getString());
  }

  /**
   * Метод изменяющий рост выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   * @throws IncorrectFloatException при некорректном вводе роста
   */
  private void changeHeightHandler(Human humanToEdit) throws IncorrectFloatException {
    out.println("Введите рост в метрах:");
    humanToEdit.setHeight(inp.getFloat());
  }

  /**
   * Метод изменяющий вес выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   * @throws IncorrectFloatException при некорректном вводе веса
   */
  private void changeWeightHandler(Human humanToEdit) throws IncorrectFloatException {
    out.println("Введите вес в килограммах:");
    humanToEdit.setWeight(inp.getFloat());
  }

  /**
   * Метод вывода всего списка людей
   * @throws AssertionError при пустом списке
   */
  private void printHumans() throws AssertionError {
    assert !humans.isEmpty() : "Не добавлено ни одного человека";

    int num = 1;

    for (Human human : this.humans) {
      out.printf("Человек №%d\n", num);
      out.println(human);
      out.println("####################");

      num++;
    }
  }
}
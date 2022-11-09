package com.lab5.Exceptions;

/**
 * Класс исключения, возникающего при неправильном вводе строки
 */
public class IncorrectStringException extends Exception {

  /**
   * Конструктор исключения
   *
   * @param message сообщение для исключения
   * @param line    строка, из-за которой было вызвано исключение
   */
  public IncorrectStringException(String message, String line) {
    super(message + ": " + line);
  }

}

package com.lab5.Exceptions;

/**
 * Класс исключения, возникающего при присвоении некорректного значения полям int
 */
public class IncorrectIntException extends Exception {

  /**
   * Конструктор исключения
   *
   * @param message сообщение для исключения
   * @param value   значение, из-за которого было вызвано исключение
   */
  public IncorrectIntException(String message, int value) {
    super(message + ": " + value);
  }
}

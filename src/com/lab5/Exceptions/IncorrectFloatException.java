package com.lab5.Exceptions;

/**
 * Класс исключения, возникающего при присвоении некорректного значения полям float
 */
public class IncorrectFloatException extends Exception {

  /**
   * Конструктор исключения
   *
   * @param message сообщение для исключения
   * @param value   значение, из-за которого было вызвано исключение
   */
  public IncorrectFloatException(String message, float value) {
    super(message + ": " + value);
  }
}

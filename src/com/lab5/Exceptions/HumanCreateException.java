package com.lab5.Exceptions;


/**
 * Класс исключения, возникающего при некорректном создании экземпляра человка, через конструктор с
 * параметрами
 */
public class HumanCreateException extends Exception {

  /**
   * Консттруктор исключения
   *
   * @param message сообщение для исключения
   */
  public HumanCreateException(String message, Exception cause) {
    super(message + ": " + cause.getMessage());
  }
}

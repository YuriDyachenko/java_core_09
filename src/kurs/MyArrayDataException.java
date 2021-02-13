package kurs;
/*
класс МОЕ ИСКЛЮЧЕНИЕ неправильные ДАННЫЕ в МАССИВЕ
*/
public class MyArrayDataException extends MyMultilineException {

    //основной "заголовок" задаем сразу в конструкторе
    //а к нему обычно будет присоединяться многострочная часть
    public MyArrayDataException(String message) {
        super("ОШИБКА: неправильные данные в массиве: " + message);
    }

    //отдельный конструктор, чтобы принять сразу все из накопителя
    public MyArrayDataException(MyMultilineException e) {
        this(e.getMessage());
    }

}

package kurs;
/*
класс МОЕ ИСКЛЮЧЕНИЕ неправильный РАЗМЕР МАССИВА
*/
public class MyArraySizeException extends MyMultilineException {

    //основной "заголовок" задаем сразу в конструкторе
    //а к нему обычно будет присоединяться многострочная часть
    public MyArraySizeException(String message) {
        super("ОШИБКА: неправильный размер массива: " + message);
    }

    //отдельный конструктор, чтобы принять сразу все из накопителя
    public MyArraySizeException(MyMultilineException e) {
        this(e.getMessage());
    }

}

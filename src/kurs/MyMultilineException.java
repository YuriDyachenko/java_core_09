package kurs;
/*
класс МОЕ МУЛЬСТИСТРОКОВОЕ ИСКЛЮЧЕНИЕ
выполняет две функции, что, скорее всего, в одном классе нежелательно делать
1. возможность накопить несколько строк ошибок во внутреннем стрингбилдере
2. работа со StackTrace в нужном формате - чисто ссылки на строки программ
3. вывод в консоль красным цветом
родитель для остальных исключений МОЕ ...
*/
public class MyMultilineException extends Exception {

    private final StringBuilder sb = new StringBuilder();
    private boolean occurred;

    public MyMultilineException() {
        this("");
        this.occurred = false;
    }

    public MyMultilineException(String message) {
        super(message);
    }

    public void append(String message) {
        sb.append("\n  ").append(message);
        occurred = true;
    }

    //переопределяем, чтобы к стандартному "заголовку" ошибки добавлялись внизу строки
    @Override
    public String getMessage() {
        return super.getMessage() + sb.toString();
    }

    public boolean errorOccurred() {
        return occurred;
    }

    public void clear() {
        sb.setLength(0);
        occurred = false;
    }

    public String getShortStackTrace() {
        StringBuilder stringBuilder = new StringBuilder();
        StackTraceElement[] stackTrace = getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            stringBuilder.append("в ").append(stackTrace[i]);
            if (i != stackTrace.length - 1)
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void printShortStackTrace() {
        printRed(getShortStackTrace());
    }

    public void printRed(String message) {
        System.out.println(AnsiColors.RED + message + AnsiColors.RESET);
    }

    public void printRedMessage() {
        printRed("\n" + getMessage());
    }

}

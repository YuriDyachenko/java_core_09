package kurs;
/*
1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4.
При подаче массива другого размера необходимо бросить исключение MyArraySizeException.
2. Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать.
Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ
или текст вместо числа), должно быть брошено исключение MyArrayDataException с детализацией,
в какой именно ячейке лежат неверные данные.
3. В методе main() вызвать полученный метод, обработать возможные исключения MyArraySizeException и
MyArrayDataException и вывести результат расчета.
*/
public class Main {

    public static void main(String[] args) {

        String[][] rightArray = {
                {"0", "1", "2", "3"},
                {"4", "5", "6", "7"},
                {"8", "9", "10", "11"},
                {"12", "13", "14", "15"}
        };
        printArray(rightArray, "----- Правильный массив -----");
        testArray(rightArray);

        String[][] wrongSizeArray = {
                {"0", "1", "2", "3"},
                {"4", "5", "6"},
                {"8", "9"},
                {"10"}
        };
        printArray(wrongSizeArray, "----- Неправильные размеры -----");
        testArray(wrongSizeArray);

        String[][] wrongDataArray = {
                {"0", "1", "2", "3"},
                {"4", "5", "6", "7"},
                {"8", "9", "A", "B"},
                {"C", "D", "E", "F"}
        };
        printArray(wrongDataArray, "----- Неправильные данные -----");
        testArray(wrongDataArray);
   }

    //метод для суммирования каждого подготовленного массива, чтобы не дублировать код
    public static void testArray(String[][] a) {
        try {
            System.out.printf("%d\n", sumArray(a));
        }
        catch (MyArraySizeException | MyArrayDataException e) {
            //вывод красного основного сообщения, без StackTrace
            e.printRedMessage();
            //вывод StackTrace именно своим методом и тоже красным цветом
            //почему не стандартным методом printStackTrace?
            //потому что этот метод на самом деле не выводит в "текущую консоль"
            //он пишет не в System.out, а в System.err, который будет выведен в консоль
            //автоматически уже после отработки всей программы, а зачем оно нам там?
            //кроме того, стандартный выводит не только StackTrace, но и всю ошибку целиком,
            //включая имя класса
            e.printShortStackTrace();
        }
    }

    //собственно суммирование с выбросом исключений
    public static int sumArray(String[][] a) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;

        //исключения "размера" и "данных" тоже мультистрочные, так как наследники
        //но!!!
        //если здесь сразу создать нужное исключение, то именно на эту строку будет
        //переходить ссылка из StackTrace
        //поэтому используем родитель, чисто для накопления строк ошибок
        //здесь вполне мог справиться StringBuilder
        MyMultilineException e = new MyMultilineException();

        //проверка всех возможных "неправильных размеров"
        //сначала число строк
        if (a.length != 4)
            e.append(String.format("число строк = %d", a.length));
        //число элементов в каждой строке
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != 4)
                e.append(String.format("число элементов в строке %d = %d", i + 1, a[i].length));
        }
        //а уж если в нем что-то накопилось, то создаем именно нужное исключение
        //и ссылка из StackTrace будет вести именно в эту строку
        if (e.errorOccurred())
            throw new MyArraySizeException(e);

        //очистим "накопитель" перед повторным использованием
        e.clear();
        //теперь можно и суммировать
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                String s = a[i][j];
                try {
                    int n = Integer.parseInt(s);
                    sum += n;
                }
                catch (NumberFormatException numberFormatException) {
                    //накапливаем ошибки преобразования всех элементов
                    e.append(String.format("[%d][%d] = %s", i + 1, j + 1, s));
                }
            }
        }
        //аналогично создаем нужное исключение
        if (e.errorOccurred())
            throw new MyArrayDataException(e);

        return sum;
    }

    //обычная печать массива с заголовком и "футером" )
    public static void printArray(String[][] a, String header) {
        System.out.println("\n" + header);
        for (String[] row : a) {
            for (String cell : row) {
                System.out.printf("  %s%s", cell.length() < 2 ? " " : "", cell);
            }
            System.out.println();
        }
        System.out.print("Считаем сумму: ");
    }

}

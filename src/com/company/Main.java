package com.company;
 /* Доброго времени суток, уважаемые менторы.
    В данном тестовом задании перчисление и методы
    преобразования римских цифр и обратно были честно
    мной "позаимствованы" с сайта codeflow.site))))).
    Весь остальной код "родила" голова;)
  */
import java.util.*;
import java.util.stream.Collectors;

class Calc {
      static int calculation(int d1, int d2, char x) throws IllegalArgumentException {
          int result = 0;
          if ((d1 > 0 & d1 <= 10)&(d2 > 0 & d2 <= 10)) {
              switch (x) {
                  case '*':
                      result = d1 * d2;
                      break;
                  case '/':
                      result = d1 / d2;
                      break;
                  case '+':
                      result = d1 + d2;
                      break;
                  case '-':
                      result = d1 - d2;
                      break;
                  default:
                      throw new IllegalArgumentException("Арифметический знак не опознан(((");
              }
          } else {
              throw new IllegalArgumentException("Цифры вне диапазона");
          }
          return result;
      }
}

enum RomanNumeral {
    I(1), IV(4), V(5),
    IX(9), X(10), XL(40),
    L(50), XC(90), C(100);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public static List<RomanNumeral> getReverseSortedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                .collect(Collectors.toList());
    }
}


class ArabRome {

    public static int romeToArab(String value) {
         String romeValue = value.toUpperCase();
         int result = 0;

         List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

         int i = 0;

         while ((romeValue.length() > 0) && (i < romanNumerals.size())) {
             RomanNumeral symbol = romanNumerals.get(i);
             if (romeValue.startsWith(symbol.name())) {
                 result += symbol.getValue();
                 romeValue = romeValue.substring(symbol.name().length());
             } else {
                 i++;
             }
         }
         if (romeValue.length() > 0) {
             throw new IllegalArgumentException(value + " не является римской цифрой");
         }
         return result;
    }

    public static String arabToRome(int value) {
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((value > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= value) {
                sb.append(currentSymbol.name());
                value -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
}
public class Main {
/* данный класс чуствую можно было написать гораздо эффективнее,
    но мой мозг смог пока выдать только такие "городушки"))))) */

    public static void main(String[] args) {
        int arabVal1;
        int arabVal2;
        char sign;
        String romeVal1;
        String romeVal2;
        int arabResult = 0;
        String romeResult;

        System.out.println("Введите пример в одну строку через пробел:");
        Scanner scanner = new Scanner(System.in);
        try {
            if (scanner.hasNextInt()) {
                arabVal1 = scanner.nextInt();
                sign = scanner.next().charAt(0);
                arabVal2 = scanner.nextInt();
                arabResult = Calc.calculation(arabVal1, arabVal2, sign);
                System.out.println(arabResult);
            } else {
                romeVal1 = scanner.next();
                sign = scanner.next().charAt(0);
                if (scanner.hasNextInt()) throw new IllegalArgumentException("2-й аргумент арабская цифра");
                romeVal2 = scanner.next();
                arabVal1 = ArabRome.romeToArab(romeVal1);
                arabVal2 = ArabRome.romeToArab(romeVal2);
                arabResult = Calc.calculation(arabVal1, arabVal2, sign);
                romeResult = ArabRome.arabToRome(arabResult);
                System.out.println(romeResult);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Неверный формат записи. " + e);
            throw new IllegalArgumentException();
        }

    }
}

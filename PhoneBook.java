import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class PhoneBook {
    //valid country codes
    public static String[] validCodes = {"7", "10", "213"}; // валидные номера телефона

    //do homework here

    public static boolean tenNumbers(String phone) {        // метод проходит по строке с номером и проверяет, что в строке 10 элементов и это цифры
        if (phone.length() == 10) {
            for (int j = 0; j < 10; j++) {
                if (!Character.isDigit(phone.charAt(j))) {
                    return false;
                }
            }
            return true;
        } else return false;
    }

    public static boolean validate(String phone) {       // проверка строки номера на соответсвтие услвоиям
        if (phone.length() >= 12 && phone.length() <= 14) {
            if (phone.charAt(0) == '+') {
                if (phone.charAt(1) == '7' && tenNumbers(phone.substring(2))) {
                    return true;
                } else if (phone.charAt(1) == '1' && phone.charAt(2) == '0' && tenNumbers(phone.substring(3))) {
                    return true;
                } else if (phone.charAt(1) == '2' && phone.charAt(2) == '1' && phone.charAt(3) == '3' && tenNumbers(phone.substring(4))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

     public static BigInteger[] sort(BigInteger[] mas) {         // сортировка Таноса
        if (mas.length <= 1) {                                  // условие на выход из цикла (иначе крутимся вечно)
            return mas;
        } else {
            BigInteger sum = new BigInteger("0");           // переходим к типу BigInteger и BigDecimal так как число очень большие, все остальное пересорт будет
            for (int i = 0; i < mas.length; i++) {
                sum = sum.add(mas[i]);                         // проходим по массиву и считаем сумму всех числе прошедших валидацию
            }
            BigDecimal av = new BigDecimal(sum);               // почему от sum?
            BigDecimal l = new BigDecimal(mas.length);
            av = av.divide(l, RoundingMode.CEILING);           // считаем среднее значение
            ArrayList<BigInteger> masLeft = new ArrayList<BigInteger>();    // создаем листы, чтобы была возможность работать с добавлением элементов
            ArrayList<BigInteger> masRight = new ArrayList<BigInteger>();
            boolean flag = true;
            for (int i = 0; i < mas.length; i++) {                          // условие попадание в левый или правый подмассив
                if (!(new BigDecimal(mas[i]).compareTo(av) == 0)) {
                    flag = false;
                }
                if (new BigDecimal(mas[i]).compareTo(av) == -1) {
                    masLeft.add(mas[i]);
                } else {
                    masRight.add(mas[i]);
                }
            }
            if (flag) {
                return mas;
            }
            BigInteger[] left = new BigInteger[masLeft.size()];       // создаем новые массивы
            BigInteger[] right = new BigInteger[masRight.size()];
            for (int i = 0; i < left.length; i++) {
                left[i] = masLeft.get(i);
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = masRight.get(i);
            }
            return Stream.concat(Arrays.stream(sort(left)), Arrays.stream(sort(right))).toArray(BigInteger[]::new);   // рекурсия для сортировки
        }
    }
    public static String[] validateAndSort(String[] unSortedPhones){                // основная программа (понять)
        ArrayList<BigInteger> validated = new ArrayList<BigInteger>();
        for (int i = 0; i < unSortedPhones.length; i++) {
            if (validate(unSortedPhones[i])) {
                validated.add(new BigInteger(unSortedPhones[i].substring(1)));
            }
        }
        BigInteger[] validatedNumbers = new BigInteger[validated.size()];
        for (int i = 0; i < validatedNumbers.length; i++) {
            validatedNumbers[i] = validated.get(i);
        }
        var res = sort(validatedNumbers);
        String[] ans = new String[res.length];
        for (int i = 0; i < res.length; i++) {
            ans[i] = '+' + res[i].toString();
        }
        return ans;
    }

};


package numericalMethods;

/**
 * решение СЛАУ вида Ax=b методом LUP разложения матрицы
 * элементы матрицы L и U для экономии места находятся в исходной матрице
 * матрица перестановок тоже в целях экономии задана одномерным массивом
 * Описание алгоритма:
 * сначало алгоритм высчитывает элементы матриц P,U,L для этого применяется метод упрощений Гаусса,
 * элементами L являются элементы ниже главной диагонали, U - элементы матрицы выше главной диагонали.
 * После построения 3 матриц, производится прямая перестановка для матрицы L=Pb,решением системы будет y
 * Затем обратная перестановка для U=y , решением будет x - корни исходной системы уравний
 *
 * @author Artem Konovalov
 *         Created with IntelliJ IDEA.
 *         Date: 23.09.12
 *         Time: 0:59
 */

public class Lup {
    public static double[] calculate(double[][] array) throws IllegalArgumentException {
        if (array.length != array[0].length - 1) {
            throw new IllegalArgumentException("число уравнений должно быть равно количеству неизвестных");
        }
        int[] p = new int[array.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = i;
        }

        //факторизация матрицы A
        for (int k = 0; k < array[0].length - 1; k++) {
            double max = 0;
            int numberRowForReplace = k;
            for (int j = k; j < array.length; j++) {
                if (Math.abs(array[j][k]) > max) {
                    numberRowForReplace = j;
                    max = Math.abs(array[j][k]);
                }
            }
            if (max == 0) {
                throw new IllegalArgumentException("матрица не должна быть вырожденной");
            }

            //меняем местами номера строк, которые будут переставлены
            int value = p[k];
            p[k] = p[numberRowForReplace];
            p[numberRowForReplace] = value;

            //переставляем соотвествующие строки
            double[] row = array[k];
            array[k] = array[numberRowForReplace];
            array[numberRowForReplace] = row;

            //прямая подстановка
            for (int i = k + 1; i < array.length; i++) {
                //делим элементы ниже главной диагонали на ведущий
                array[i][k] = array[i][k] / array[k][k];
                for (int j = k + 1; j < array.length; j++) {
                    //вычитаем элементы текущей k-строчки помноженные на коэффициент из элементов строчек ниже ее
                    array[i][j] = array[i][j] - array[i][k] * array[k][j];
                }
            }
        }

        // прямая подстановка
        // вычисление y системы Ly=Pb
        double[] y = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            double value = 0;
            for (int j = 0; j < i; j++) {
                value += (i == j ? 1 : array[i][j]) * y[j];
            }
            y[i] = array[i][array[0].length - 1] - value;
        }


        // обратная подстановка
        // вычисление x системы Ux=y

        double[] x = new double[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            double value = 0;
            for (int j = i + 1; j < array.length; j++) {
                value += array[i][j] * x[j];
            }
            x[i] = (y[i] - value) / array[i][i];
        }

        return x;
    }
}

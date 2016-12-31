package ru.izebit.structs.list;

import ru.izebit.FunctionUtils.TernaryFunction;

import java.util.Objects;
import java.util.function.BiFunction;

import static ru.izebit.FunctionUtils.Recursion;

/**
 *  @see <a href="https://en.wikipedia.org/wiki/Conc-Tree_list">conc-treelist</a>
 */
public abstract class ConcTreeList<T> {

    public final int size;
    protected final int level;
    protected final ConcTreeList<T> left;
    protected final ConcTreeList<T> right;

    protected ConcTreeList(int size,
                           int level,
                           ConcTreeList<T> left,
                           ConcTreeList<T> right) {

        this.size = size;
        this.level = level;
        this.left = left;
        this.right = right;
    }


    /**
     * @param <T> тип элементов в списке
     * @return пустой список
     */
    @SuppressWarnings("unchecked")
    public static <T> ConcTreeList<T> empty() {
        return Empty.EMPTY;
    }

    private static void rangeCheck(int index) {
        if (index < 0)
            throw new IllegalArgumentException("index must be more zero");
    }

    /**
     * добавление елемента в конец списка
     *
     * @param element элемент
     * @return список
     */
    public ConcTreeList<T> add(T element) {
        return this.concat(new Single<>(element));
    }

    /**
     * изменение значения
     *
     * @param index   индекс изменяемой ячейки
     * @param element новый элемент
     * @return список с измененными данными
     */
    public ConcTreeList<T> set(int index, T element) {
        if (size <= index)
            throw new IllegalArgumentException("index must be less list size");


        Recursion<BiFunction<ConcTreeList<T>, Integer, ConcTreeList<T>>> rec = new Recursion<>();

        rec.function =
                (node, idx) -> {
                    if (node instanceof Single)
                        return new Single<>(element);

                    else if (node instanceof Concat) {

                        Concat<T> concat = (Concat<T>) node;
                        if (index < concat.left.size)
                            return new Concat<>(rec.function.apply(node.left, index), node.right);
                        else
                            return new Concat<>(node.left, rec.function.apply(node.right, index - node.left.size));

                    } else
                        throw new IllegalArgumentException("list is empty");


                };

        return rec.function.apply(this, index);
    }

    /**
     * проверка на принадлежность
     *
     * @param element элемент
     * @return true если element содержится в списке иначе false
     */
    public boolean contains(T element) {
        Recursion<BiFunction<ConcTreeList<T>, T, Boolean>> rec = new Recursion<>();
        rec.function =
                (node, e) -> {
                    if (node instanceof Single)
                        return Objects.equals(((Single<T>) node).element, e);

                    if (node instanceof Concat) {
                        Concat<T> concat = (Concat<T>) node;
                        return rec.function.apply(concat.left, e) || rec.function.apply(concat.right, e);
                    }

                    return false;
                };

        return rec.function.apply(this, element);
    }

    /**
     * объединение списков
     *
     * @param other другой список
     * @return новый список содержащий элементы из двух исходных
     */
    public ConcTreeList<T> concat(ConcTreeList<T> other) {
        if (other == null)
            throw new IllegalArgumentException("other must be not null");

        if (other.getClass() == Empty.class)
            return this;
        if (this.getClass() == Empty.class)
            return other;


        int diff = other.level - this.level;

        if (Math.abs(diff) <= 1)
            return new Concat<>(this, other);

        //левое дерево меньше чем правое
        if (diff < -1) {
            //у левого поддерева уровней больше чем у правого
            if (this.left.level >= this.right.level) {
                ConcTreeList<T> conc = this.right.concat(other);
                return new Concat<>(this.left, conc);
            }
            //правое поддерево все таки больше
            ConcTreeList<T> rightSubTree = this.right.right.concat(other);
            if (rightSubTree.level == this.level - 3) {
                ConcTreeList<T> leftSubTree = this.right.left.concat(rightSubTree);
                return new Concat<>(this.left, leftSubTree);
            }
            //слиянием без участие потомков
            ConcTreeList<T> leftSubTree = this.left.concat(this.right.left);
            return new Concat<>(leftSubTree, rightSubTree);
        }

        //симметрично если правое дерево меньшеx
        //правое поддерево правого дерева больше левого поддерева
        if (other.right.level >= other.left.level) {
            ConcTreeList<T> conc = this.concat(other.left);
            return new Concat<>(conc, other.right);
        }

        ConcTreeList<T> leftSubTree = this.concat(other.left.left);
        if (leftSubTree.level == other.level - 3) {
            ConcTreeList<T> rightSubTree = leftSubTree.concat(other.left.right);
            return new Concat<>(rightSubTree, other.right);
        }

        ConcTreeList<T> rightSubTree = other.left.right.concat(other.right);
        return new Concat<>(leftSubTree, rightSubTree);
    }

    /**
     * получить элемент по индексу
     *
     * @param index индекс
     * @return элемент если он содержится иначе null
     */
    public T get(int index) {
        rangeCheck(index);

        Recursion<BiFunction<ConcTreeList<T>, Integer, T>> rec = new Recursion<>();
        rec.function =
                (node, idx) -> {
                    if (node instanceof Single && idx == 0)
                        return ((Single<T>) node).element;


                    if (node instanceof Concat) {
                        Concat<T> concat = (Concat<T>) node;
                        if (idx < concat.left.size)
                            return rec.function.apply(concat.left, idx);
                        else
                            return rec.function.apply(concat.right, idx - concat.left.size);
                    }

                    return null;
                };

        return rec.function.apply(this, index);
    }

    /**
     * вставка элемента на указанную позицию
     *
     * @param index   индекс, если он больше size то вызов будет аналогичем методу add
     * @param element элемент
     * @return список
     * @throws IllegalArgumentException если index меньше нуля
     */
    public ConcTreeList<T> insert(int index, T element) {
        rangeCheck(index);

        Recursion<TernaryFunction<ConcTreeList<T>, Integer, T, ConcTreeList<T>>> rec = new Recursion<>();
        rec.function =
                (node, idx, e) -> {
                    if (node instanceof Concat) {

                        Concat<T> concat = (Concat<T>) node;
                        if (idx < concat.left.size)
                            return new Concat<>(rec.function.apply(concat.left, idx, e), concat.right);
                        else
                            return new Concat<>(concat.left, rec.function.apply(concat.right, idx - concat.left.size, e));

                    } else if (node instanceof Single) {

                        if (idx == 0)
                            return new Concat<>(new Single<>(e), node);
                        else
                            return new Concat<>(node, new Single<>(e));

                    } else
                        return new Single<>(e);
                };


        return rec.function.apply(this, index, element);
    }

    private static class Empty<E> extends ConcTreeList<E> {
        private static final Empty EMPTY = new Empty<>();

        @SuppressWarnings("unchecked")
        private Empty() {
            super(0, 0, EMPTY, EMPTY);
        }
    }

    private static class Single<T> extends ConcTreeList<T> {
        final T element;

        @SuppressWarnings("unchecked")
        protected Single(T element) {
            super(1, 0, Empty.EMPTY, Empty.EMPTY);

            this.element = element;
        }
    }

    private static class Concat<T> extends ConcTreeList<T> {
        private Concat(ConcTreeList<T> left, ConcTreeList<T> right) {
            super(left.size + right.size, 1 + Math.max(left.level, right.level), left, right);
        }
    }
}

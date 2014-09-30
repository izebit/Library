package structs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 08.09.12
 * Time: 16:12
 *
 * @author Artem Konovalov
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private static final Node NIL = new Node(null, null);

    static {
        NIL.parent = NIL;
        NIL.leftChild = NIL;
        NIL.rightChild = NIL;
        NIL.colour = Colour.black;
    }

    private int size;
    private Node<K, V> head;

    /*
    @return возвращает количество записей в дереве
     */
    public int size() {
        return size;
    }

    public RedBlackTree() {
        head = NIL;
        size = 0;
    }

    /*
    возвращает значение хранящейся под таким ключом
    @param key ключ для которого следует вернуть значение
    @return значение записи, если ключа с такой записью нет null
     */
    public V get(K key) {
        if (head != NIL) {
            Node<K, V> current = head;
            while (current != NIL) {
                if (current.key.compareTo(key) == 0) {
                    return current.value;
                } else {
                    if (current.key.compareTo(key) > 0) {
                        current = current.leftChild;
                    } else {
                        current = current.rightChild;
                    }
                }
            }
        }
        return null;
    }

    /*
     удаляет запись с заданным ключом из дерева
     @param key ключ, запись с котором следует удалить
     @return true если запись с таким ключом есть, иначе false
     */
    public boolean remove(K key) {
        if (head != NIL) {
            Node<K, V> current = head;
            while (current != NIL) {
                if (current.key.compareTo(key) == 0) {
                    break;
                } else {
                    if (current.key.compareTo(key) > 0) {
                        current = current.leftChild;
                    } else {
                        current = current.rightChild;
                    }
                }
            }
            //вершина с заданным ключом есть в дереве
            if (current != NIL) {
                size--;
                simpleRemoveNode(current);
                head.colour = Colour.black;
            }
        }
        return false;
    }

    private void simpleRemoveNode(Node<K, V> node) {
        if (node.leftChild == NIL && node.rightChild == NIL) {
            //вершина лист, и она черная - требуется соблюсти красночерные свойства
            if (node.colour == Colour.black) {
                fixUpBeforeRemove(node);
            }
            //удаление вершины
            if (node.parent.leftChild == node) {
                node.parent.setLeftChild(NIL);
            } else {
                node.parent.setRightChild(NIL);
            }
            head = (node == head) ? node.parent : head;
        } else {
            //имеет двух потомков
            if (node.leftChild != NIL && node.rightChild != NIL) {
                Node<K, V> next;
                Node<K, V> intermediateNode = node.rightChild;
                do {
                    next = intermediateNode;
                    intermediateNode = intermediateNode.leftChild;
                } while (intermediateNode != NIL);

                node.key = next.key;
                node.value = next.value;

                simpleRemoveNode(next);

            } else {
                //имеет одного потомка
                if (node.leftChild != NIL) {
                    if (node.parent.leftChild == node) {
                        node.parent.setLeftChild(node.leftChild);
                    } else {
                        node.parent.setRightChild(node.leftChild);
                    }
                    node.leftChild.setParent(node.parent);
                    head = (head == node) ? node.leftChild : head;

                    if (node.colour == Colour.black) {
                        fixUpBeforeRemove(node.leftChild);
                    }
                } else {
                    if (node.parent.leftChild == node) {
                        node.parent.setLeftChild(node.rightChild);
                    } else {
                        node.parent.setRightChild(node.rightChild);
                    }
                    node.rightChild.setParent(node.parent);
                    head = (head == node) ? node.rightChild : head;

                    if (node.colour == Colour.black) {
                        fixUpBeforeRemove(node.rightChild);
                    }
                }
            }
        }
    }

    private void fixUpBeforeRemove(Node<K, V> node) {
        if (node == head) {
            return;
        }

        if (node.colour == Colour.red) {
            node.setColour(Colour.black);
        } else {
            //брат красный
            Node<K, V> brother = node.getBrother();
            if (brother.colour == Colour.red) {
                node.parent.setColour(Colour.red);
                brother.setColour(Colour.black);
                if (node.parent.rightChild == node) {
                    rightRotate(node.parent);
                } else {
                    leftRotate(node.parent);
                }
            }
            //брат и оба его потомка черные
            brother = node.getBrother();
            if (brother.leftChild.colour == Colour.black && brother.rightChild.colour == Colour.black) {
                brother.setColour(Colour.red);
                fixUpBeforeRemove(node.parent);
            } else {

                //брат и его одинаковый потомок черные
                boolean bothRight = node.parent.leftChild == node && node.parent.rightChild.rightChild.colour == Colour.black;
                boolean bothLeft = node.parent.rightChild == node && node.parent.leftChild.leftChild.colour == Colour.black;
                if (bothLeft || bothRight) {
                    brother.setColour(Colour.red);
                    if (bothRight) {
                        brother.leftChild.setColour(Colour.black);
                        rightRotate(brother);
                    } else {
                        brother.rightChild.setColour(Colour.black);
                        leftRotate(brother);
                    }
                }

                //брат черный а его одинаковый потомок красный
                brother = node.getBrother();
                bothRight = node.parent.leftChild == node && brother.rightChild.colour == Colour.red;
                bothLeft = node.parent.rightChild == node && brother.leftChild.colour == Colour.red;
                if (bothLeft || bothRight) {
                    brother.setColour(node.parent.colour);
                    node.parent.setColour(Colour.black);
                    if (bothRight) {
                        brother.rightChild.setColour(Colour.black);
                        leftRotate(node.parent);
                    } else {
                        brother.leftChild.setColour(Colour.black);
                        rightRotate(node.parent);
                    }
                }
            }
        }
    }

    /*
     добавляет пару ключ значение в дерево, если в дереве есть уже пара с таким ключом,
     то добавления не происходит
     @param key ключ
     @param value значение
     @return false если в дереве есть уже пара с таким ключом, true если нет
    */
    public boolean add(K key, V value) {
        if (head == NIL) {
            head = new Node<K, V>(key, value);
            head.setColour(Colour.black);
        } else {
            Node<K, V> parent = head;
            Node<K, V> current = head;
            while (current != NIL) {
                if (current.key.compareTo(key) == 0) {
                    return false;
                } else {
                    parent = current;
                    if (current.key.compareTo(key) > 0) {
                        current = current.leftChild;
                    } else {
                        current = current.rightChild;
                    }
                }
            }

            current = new Node<K, V>(key, value);
            current.parent = parent;
            if (current.key.compareTo(parent.key) > 0) {
                parent.setRightChild(current);
            } else {
                parent.setLeftChild(current);
            }

            //если родитель красный то возможно нарушение красно-черных свойств
            if (parent.colour == Colour.red) {
                fixUpAfterAdd(current);
            }
        }
        size++;
        return true;
    }

    private void fixUpAfterAdd(Node<K, V> node) {
        while (node != head && node.parent.colour == Colour.red) {
            //дядя красный
            Node<K, V> uncle = node.getUncle();
            if (uncle.colour == Colour.red) {
                node.parent.setColour(Colour.black);
                uncle.setColour(Colour.black);
                node.parent.parent.setColour(Colour.red);
                node = node.parent.parent;
            } else {
                //родитель и потомок одинаковые дети и оба красные
                boolean bothLeft = node.parent.parent.leftChild == node.parent && node.parent.leftChild == node;
                boolean bothRight = node.parent.parent.rightChild == node.parent && node.parent.rightChild == node;
                if (bothLeft || bothRight) {
                    node.parent.setColour(Colour.black);
                    node.parent.parent.setColour(Colour.red);
                    if (bothLeft) {
                        rightRotate(node.parent.parent);
                    } else {
                        leftRotate(node.parent.parent);
                    }
                }
                //родитель и потомок разные дети
                else {
                    //родитель левый ребенок, потомок правый
                    if (node.parent.parent.leftChild == node.parent) {
                        leftRotate(node.parent);
                        fixUpAfterAdd(node.leftChild);
                    }
                    //родиль правый ребенок, потомок левый
                    else {
                        rightRotate(node.parent);
                        fixUpAfterAdd(node.rightChild);
                    }
                }
                break;
            }
        }

        head.colour = Colour.black;
    }

    private void leftRotate(Node<K, V> node) {
        if (node.rightChild == NIL) {
            return;
        }
        if (node == head) {
            head = node.rightChild;
        }

        if (node.parent.leftChild == node) {
            node.parent.setLeftChild(node.rightChild);
        } else {
            node.parent.setRightChild(node.rightChild);
        }

        node.rightChild.setParent(node.parent);
        node.setParent(node.rightChild);

        node.parent.leftChild.setParent(node);
        node.setRightChild(node.parent.leftChild);
        node.parent.setLeftChild(node);
    }

    private void rightRotate(Node<K, V> node) {
        if (node.leftChild == NIL) {
            return;
        }
        if (node == head) {
            head = node.leftChild;
        }

        if (node.parent.leftChild == node) {
            node.parent.setLeftChild(node.leftChild);
        } else {
            node.parent.setRightChild(node.leftChild);
        }

        node.leftChild.setParent(node.parent);
        node.setParent(node.leftChild);

        node.parent.rightChild.setParent(node);
        node.setLeftChild(node.parent.rightChild);
        node.parent.setRightChild(node);
    }

    //рекурсивно обходит дерево
    public void show(Node node) {
        if (node == NIL) {
            return;
        }

        if (node == null) {
            node = head;
        }
        System.out.println(node.key + "  " + node.colour + " |" + node.leftChild.key + "|" + node.rightChild.key + "|");
        show(node.leftChild);
        show(node.rightChild);
    }

    //проверяет коректность красно-черных свойств
    public void test(Node<K, V> node, int count, List<Integer> results) {
        if (node == null) {
            List<Integer> list = new ArrayList<Integer>();
            test(head, 0, list);
            for (int i = 1; i < list.size(); i++) {
                if (!list.get(i - 1).equals(list.get(i))) {
                    System.out.println("fail");
                    System.out.println(list.toString());
                    return;
                }
            }
        } else {
            if (node == NIL) {
                return;
            }
            if (node.colour == Colour.black) {
                count++;
            }
            if (node.leftChild == NIL && node.rightChild == NIL) {
                results.add(count);
                return;
            }

            test(node.leftChild, count, results);
            test(node.rightChild, count, results);
        }
    }


    private static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private Colour colour;

        private Node<K, V> parent;
        private Node<K, V> leftChild;
        private Node<K, V> rightChild;

        private Node(K key, V value) {
            parent = NIL;
            leftChild = NIL;
            rightChild = NIL;

            this.value = value;
            this.key = key;
            colour = Colour.red;
        }

        private void setParent(Node<K, V> node) {
            if (this != NIL) {
                this.parent = node;
            }
        }

        private void setLeftChild(Node<K, V> node) {
            if (this != NIL) {
                this.leftChild = node;
            }
        }

        private void setRightChild(Node<K, V> node) {
            if (this != NIL) {
                this.rightChild = node;
            }
        }

        private void setColour(Colour colour) {
            if (this != NIL) {
                this.colour = colour;
            }
        }

        private Node<K, V> getBrother() {
            return (parent.leftChild == this) ? parent.rightChild : parent.leftChild;
        }

        private Node<K, V> getUncle() {
            return (parent.parent.leftChild == parent) ? parent.parent.rightChild : parent.parent.leftChild;
        }
    }

    private static enum Colour {
        black,
        red
    }
}

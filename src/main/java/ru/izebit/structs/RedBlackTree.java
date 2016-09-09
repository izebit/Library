package ru.izebit.structs;

/**
 * Date: 08.09.12
 * Time: 16:12
 *
 * @author Artem Konovalov
 */
public class RedBlackTree<K extends Comparable<? super K>, V> {
    @SuppressWarnings("unchecked")
    static final Node NIL = new Node<>(null, null);

    static {
        NIL.parent = NIL;
        NIL.leftChild = NIL;
        NIL.rightChild = NIL;
        NIL.colour = Colour.BLACK;
    }

    Node<K, V> head;
    private int size;

    @SuppressWarnings("unchecked")
    public RedBlackTree() {
        head = NIL;
        size = 0;
    }

    private static String show(Node node) {
        if (node == NIL)
            return "";

        String result = (node.key + "  " + node.colour + " |" + node.leftChild.key + "|" + node.rightChild.key + "|");
        return result + show(node.leftChild) + show(node.rightChild);
    }

    /**
     * @return возвращает количество записей в дереве
     */
    public int size() {
        return size;
    }

    /**
     * возвращает значение хранящейся под таким ключом
     *
     * @param key ключ для которого следует вернуть значение
     * @return значение записи, если ключа с такой записью нет null
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

    /**
     * удаляет запись с заданным ключом из дерева
     *
     * @param key ключ, запись с котором следует удалить
     * @return true если запись с таким ключом есть, иначе false
     * @throws IllegalArgumentException ключ не должен быть null
     */
    public boolean remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("key must be is not null");

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
                head.colour = Colour.BLACK;
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void simpleRemoveNode(Node<K, V> node) {
        if (node.leftChild == NIL && node.rightChild == NIL) {
            //вершина лист, и она черная - требуется соблюсти красночерные свойства
            if (node.colour == Colour.BLACK) {
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

                    if (node.colour == Colour.BLACK) {
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

                    if (node.colour == Colour.BLACK) {
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

        if (node.colour == Colour.RED) {
            node.setColour(Colour.BLACK);
        } else {
            //брат красный
            Node<K, V> brother = node.getBrother();
            if (brother.colour == Colour.RED) {
                node.parent.setColour(Colour.RED);
                brother.setColour(Colour.BLACK);
                if (node.parent.rightChild == node) {
                    rightRotate(node.parent);
                } else {
                    leftRotate(node.parent);
                }
            }
            //брат и оба его потомка черные
            brother = node.getBrother();
            if (brother.leftChild.colour == Colour.BLACK && brother.rightChild.colour == Colour.BLACK) {
                brother.setColour(Colour.RED);
                fixUpBeforeRemove(node.parent);
            } else {

                //брат и его одинаковый потомок черные
                boolean bothRight = node.parent.leftChild == node && node.parent.rightChild.rightChild.colour == Colour.BLACK;
                boolean bothLeft = node.parent.rightChild == node && node.parent.leftChild.leftChild.colour == Colour.BLACK;
                if (bothLeft || bothRight) {
                    brother.setColour(Colour.RED);
                    if (bothRight) {
                        brother.leftChild.setColour(Colour.BLACK);
                        rightRotate(brother);
                    } else {
                        brother.rightChild.setColour(Colour.BLACK);
                        leftRotate(brother);
                    }
                }

                //брат черный а его одинаковый потомок красный
                brother = node.getBrother();
                bothRight = node.parent.leftChild == node && brother.rightChild.colour == Colour.RED;
                bothLeft = node.parent.rightChild == node && brother.leftChild.colour == Colour.RED;
                if (bothLeft || bothRight) {
                    brother.setColour(node.parent.colour);
                    node.parent.setColour(Colour.BLACK);
                    if (bothRight) {
                        brother.rightChild.setColour(Colour.BLACK);
                        leftRotate(node.parent);
                    } else {
                        brother.leftChild.setColour(Colour.BLACK);
                        rightRotate(node.parent);
                    }
                }
            }
        }
    }

    /**
     * добавляет пару ключ значение в дерево, если в дереве есть уже пара с таким ключом,
     * то добавления не происходит
     *
     * @param key   ключ
     * @param value значение
     * @return false если в дереве есть уже пара с таким ключом, true если нет
     * @throws IllegalArgumentException key не должен быть null
     */
    public boolean add(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("key must be is not null");

        if (head == NIL) {
            head = new Node<>(key, value);
            head.setColour(Colour.BLACK);
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

            current = new Node<>(key, value);
            current.parent = parent;
            if (current.key.compareTo(parent.key) > 0) {
                parent.setRightChild(current);
            } else {
                parent.setLeftChild(current);
            }

            //если родитель красный то возможно нарушение красно-черных свойств
            if (parent.colour == Colour.RED) {
                fixUpAfterAdd(current);
            }
        }
        size++;
        return true;
    }

    private void fixUpAfterAdd(Node<K, V> node) {
        while (node != head && node.parent.colour == Colour.RED) {
            //дядя красный
            Node<K, V> uncle = node.getUncle();
            if (uncle.colour == Colour.RED) {
                node.parent.setColour(Colour.BLACK);
                uncle.setColour(Colour.BLACK);
                node.parent.parent.setColour(Colour.RED);
                node = node.parent.parent;
            } else {
                //родитель и потомок одинаковые дети и оба красные
                boolean bothLeft = node.parent.parent.leftChild == node.parent && node.parent.leftChild == node;
                boolean bothRight = node.parent.parent.rightChild == node.parent && node.parent.rightChild == node;
                if (bothLeft || bothRight) {
                    node.parent.setColour(Colour.BLACK);
                    node.parent.parent.setColour(Colour.RED);
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

        head.colour = Colour.BLACK;
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

    /**
     * рекурсивно обходит дерево
     *
     * @return строка представление дерева
     */
    public String show() {
        return show(head);
    }


    enum Colour {
        BLACK,
        RED
    }

    static class Node<K extends Comparable<? super K>, V> {
        Colour colour;
        Node<K, V> leftChild;
        Node<K, V> rightChild;
        private K key;
        private V value;
        private Node<K, V> parent;

        @SuppressWarnings("unchecked")
        private Node(K key, V value) {
            parent = NIL;
            leftChild = NIL;
            rightChild = NIL;

            this.value = value;
            this.key = key;
            colour = Colour.RED;
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
}

package RBTree.src;

public class RBNode<AnyType> {
    String color ;
    AnyType key;
    RBNode<AnyType> leftChild;
    RBNode<AnyType> rightChild;
    RBNode<AnyType> parent;


    public RBNode(String color, AnyType key){
        //调自己的重载构造
        this(color,key,null,null,null);
    }

    public RBNode(String color, AnyType key, RBNode<AnyType> leftChild, RBNode<AnyType> rightChild, RBNode<AnyType> parent) {
        this.color = color;
        this.key = key;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
    }


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

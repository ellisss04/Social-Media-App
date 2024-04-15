package socialmedia;


import java.io.Serializable;

/**
 * {@code Node()} is an object which points to the current comment its referencing and the points to
 * the comment/post its replying to.
 *
 * @author ellis mackness
 *
 * @apiNote Comment must exist before a node object is created
 *
 * @version 1.1
 * */

public class Node implements Serializable {
    private Post pointer;
    private Post current;
    private boolean isRoot = false;

    /**
     * Create a new Node object that contains a pointer to the post/comment it replies to and the current pointer is
     * the comment replying to a post/comment
     *
     * @param comment
     * @param pointer
     * */
    protected Node(Post comment, Post pointer){
        this.pointer = pointer;
        this.current = comment;
    }

    /**
     * Method for getting the pointer
     *
     * @return pointer of current instance
     * */
    public Post getPointer(){
        return this.pointer;
    }

    /**
     * Method for getting the current Post/Comment
     *
     * @return current pointer
     * */
    public Post getCurrent(){
        return this.current;
    }

    /**
     * set the pointer to a new post/comment
     *
     * @param pointer new pointer
     * */
    public void setPointer(Post pointer){
        this.pointer = pointer;
    }

    /**
     * set the current pointer to a new post/comment
     *
     * @param pointer new pointer
     * */
    public void setCurrent(Post pointer){
        this.current = pointer;
    }


    /**
     * Sets the post to be the root post in the comment tree
     *
     * @param root boolean if the post is a root post or not
     * */
    public void setRoot(boolean root){
        this.isRoot = root;
    }

    /**
     * Gets isRoot attribute
     *
     * @apiNote A root {@code Node()} is a node that has a pointer that points to a {@code Post()} object
     * @return boolean if current node is a root
     * */
    public boolean isRoot(){
        return this.isRoot;
    }

}

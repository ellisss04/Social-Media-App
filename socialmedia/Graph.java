package socialmedia;


import java.io.Serializable;
import java.util.*;

/**
 * {@code Graph()} is an object which stores the comments in reply to a root {@code Post()}
 *
 * @author ellis mackness
 *
 * @version 1.1
 *
 * */

public class Graph implements Serializable {
    protected ArrayList<Node> graphNodes;
    private int post_id;

    /**
     * Responsible for organising the comment tree structure
     *
     * @param post_id the id of the initial post
     * */
    public Graph(int post_id){
        this.post_id = post_id;
        this.graphNodes = new ArrayList<>();
    }

    /**
     * Inserts a node into the graph with two pointers
     *
     * @param pointer post that is being replied to
     * @param current current reply post
     * */
    public void insert(Post pointer, Post current){
        Node node = new Node(current, pointer);
        if(pointer == null){
            node.setRoot(true);
        }
        this.graphNodes.add(node);
    }

    /**
     * @return ArrayList containing the graphs nodes
     * */
    public ArrayList<Node> getGraph() {
        return this.graphNodes;
    }

    /**
     * Method for organising the nodes array into groups of comment reply chains.
     *
     * @return StringBuilder Object of the output
     * @implNote This method prints the post chain.
     * */
    private ArrayList<Node> orderGraphNodes(){
        ArrayList<Node> rootComments = new ArrayList<>();
        ArrayList<Node> output = new ArrayList<>();
        Stack<Integer> pointerStack = new Stack<>();

        // getting all the root comments
        for(Node node: this.graphNodes){
            if(node.getPointer().getId() == this.post_id){
                rootComments.add(node);
            }
        }

        // creating an array containing only comments and not root comments
        ArrayList<Node> comments = this.graphNodes;
        comments.removeAll(rootComments);

        // finding comment chain
        for(Node root: rootComments){
            output.add(root);
            pointerStack.push(root.getCurrent().getId());

            // ordering output in terms of the current
            while(!pointerStack.isEmpty()){
                int pointer = pointerStack.pop();
                for(Node node: comments){
                    if(node.getPointer().getId() == pointer){
                        pointerStack.push(node.getCurrent().getId());
                        output.add(node);
                    }
                }
            }
        }
        return output;
    }

    /**
     * Method generates the String of comments and replies to a post
     *
     * @return StringBuilder of comment replies
     * */
    public StringBuilder generateGraphBuilder(){
        ArrayList<Node> output = orderGraphNodes();
        Node prevNode = output.get(0);
        StringBuilder sb = new StringBuilder();
        int indent_count = 0;

        // going through each node
        for(Node node: output){
            int current_pointer = node.getPointer().getId();
            int previous_id = prevNode.getCurrent().getId();

            // checking if the post should be indented
            if(current_pointer == this.post_id){
                indent_count = 0;
            } else if(current_pointer == previous_id){
                indent_count += 1;
            }

            String indent = "";
            for(int i = 0; i < indent_count+1; i ++){
                indent += "|\t";
            }
            for(String l: node.getCurrent().toString().split(",")){
                sb.append("\n" + indent + l);
            }
            sb.append("\n" + indent);
            sb.append("->");
            prevNode = node;
        }
        return sb;
    }
}

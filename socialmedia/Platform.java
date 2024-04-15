package socialmedia;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * {@code new Platform()} takes 0 arguments, class is for storing everything to do with the platform i.e., the accounts
 *
 * @author ellis mackness
 *
 * @version 1.1
 * */

public class Platform implements Serializable {
    private ArrayList<Account> accounts = new ArrayList<>();
    public Platform(){

    }
    /**
     * Get the accounts associated with the platform
     *
     * @return ArrayList of accounts
     * */
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * Method for adding accounts to the platform
     *
     * @param account account to add to the platform
     * */
    public void addAccount(Account account){
        this.accounts.add(account);
    }

    /**
     * Method for removing an account off of the platform
     *
     * @param account account to remove
     *
     * */
    public void removeAccount(Account account){
        this.accounts.remove(account);
    }

    /**
     * Searches the account list and returns the associated account given the string handle
     *
     * @param handle the handle of the account to find (username)
     *
     * @throws HandleNotRecognisedException if the handle isn't in the system
     * @return Account found within the platform
     *
     * */
    public Account findAccount(String handle) throws HandleNotRecognisedException {
        for (Account acc: this.accounts){
            if (acc.getHandle().equals(handle)){
                return acc;
            }
        }
        throw new HandleNotRecognisedException("Handle not recognised !");
    }

    /**
     * Finds a post/comment (since {@code comment extends post}) that is linked to the accounts in this instance given the {@code id} of the post/comment
     *
     * @param id id of the comment/post
     *
     * @throws PostIDNotRecognisedException if the post/comment doesn't exist
     * */
    public Post findPost(int id) throws PostIDNotRecognisedException {
        for(Account acc: this.accounts){
            for(Post post: acc.getPosts()){
                for(Node node: post.getCommentGraph().getGraph()){
                    if(node.getCurrent().getId() == id){
                        return node.getCurrent();
                    }
                }
                if(post.getId() == id){
                    return post;
                }
            }
        }
        throw new PostIDNotRecognisedException("Post ID doesnt exist !");
    }

}

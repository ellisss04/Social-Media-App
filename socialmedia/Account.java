package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;


/**
 * @author Ellis Mackness
 *
 * @version 1.0
 *
 * */

public class Account implements Serializable {
    private String handle;
    private String description;
    private int user_id;
    private static int total_user_id = 1;
    private Stack<String> allHandles = new Stack<>();
    private static ArrayList<Account> accountList = new ArrayList<>();
    private Stack<Post> posts = new Stack<>();
    //test comment

    /**
     * Account constructor
     *
     * @param handle
     * @throws IllegalHandleException Thrown when the account name already exists
     * */
    public Account(String handle) throws IllegalHandleException, InvalidHandleException {
        // hello
        if(isHandleUnique(handle)){
            if(isHandleValid(handle)){
                this.handle = handle;

                this.user_id = total_user_id;
                total_user_id += 1;
            } else {
                throw new InvalidHandleException("Handle is not formatted correctly");
            }
        } else {
            throw new IllegalHandleException("Handle must be unique");
        }
    }

    /**
     * Verifies the account handle is valid
     *
     * @return boolean of it is valid or not
     * */
    public boolean isHandleValid(String handle){
        if(handle.length() <= 30 && handle.length() > 0 && !handle.contains(" ")){
            return true;
        }
        return false;
    }

    /**
     * Method used to check for duplicate names
     *
     * @param handle user handle to check
     * @return boolean
     * */
    public boolean isHandleUnique(String handle)  {
        for(Account account: accountList){
            if(account.getHandle().equals(handle)){
                return false;
            }
        }
        return true;
    }

    /**
     * Method for returning the users handle
     *
     * @return String
     * */
    public String getHandle(){
        return this.handle;
    }

    /**
     * Method for updating handle
     *
     * @param newHandle String of the new handle
     * @throws IllegalHandleException when duplicate handle is found
     * */
    public void updateHandle(String newHandle) throws IllegalHandleException, InvalidHandleException {
        if(isHandleUnique(newHandle)){
            if(isHandleValid(newHandle)){
                this.allHandles.push(this.handle);
                this.handle = newHandle;
            } else{
                throw new InvalidHandleException("Handle is not formatted correctly");
            }
        } else{
            throw new IllegalHandleException("Handle Must Be Unique");
        }
    }

    /**
     * Returns the user id
     *
     * @return int user_id
     * */
    public int getUserId(){
        return this.user_id;
    }

    /**
     * Method for creating a new post
     *
     * @param message Message of the post
     * @throws InvalidPostException if the message is not valid
     * */
    public void createPost(String message) throws InvalidPostException {
        Post post = new Post(this);
        post.setMessage(message);
        this.posts.add(post);
    }

    /**
     * Sets the description of the account
     * @param description String body description
     * */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Gets all {@code Post} objects linked to this account
     *
     * @return Stack containing all of the posts
     * */
    public Stack<Post> getPosts(){
        return this.posts;
    }

    /**
     * Overrides {@code Object toString()} method by returning the instance in string form
     * <br>
     * <Strong>See Example:</Strong>
     * <p>
     *     ID: 0
     *     <br>
     *     Handle: test
     *     <br>
     *     Description: test description
     *     <br>
     *     Post Count: 10
     *     <br>
     *     Endorse Count: 314
     *
     * </p>
     *
     * @return String object of this instance
     * */
    @Override
    public String toString(){
        String output = """
                ID: %d
                Handle: %s
                Description: %s
                Post Count: %d
                Endorse Count: %d 
                """;

        int endorsement_sum = 0;
        for (Post post: this.posts){
            endorsement_sum += post.getEndorsements().size();
        }

        output = String.format(output, this.user_id, this.handle, this.description, this.posts.size(), endorsement_sum);
        return output;
    }
}

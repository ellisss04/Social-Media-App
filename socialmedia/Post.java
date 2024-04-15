package socialmedia;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author ellis mackness
 *
 * @version 1.1
 * */

public class Post implements Serializable {
    protected String message;
    public int id;
    private Account account;
    private static int total_ids = 0;
    private Graph commentGraph;
    private boolean isDeleted;
    protected ArrayList<Endorsement> endorsementList = new ArrayList<>();
    private int commentCount = 0;

    /**
     * @param account Account instance that is linked to this post
     *
     * @apiNote A post can be an original post or a comment
     * */
    public Post(Account account){
        this.account = account;
        this.id = total_ids;
        total_ids += 1;
        this.commentGraph = new Graph(this.id);
    }

    /**
     * Sets the message of the post being created
     * @param msg message to be set
     * @throws InvalidPostException if the message length is greater than 100 characters
     * */
    public void setMessage(String msg) throws InvalidPostException {
        if(msg.length() > 0 && msg.length() <= 100){
            this.message = msg;
        } else{
            throw new InvalidPostException("Message Length is Invalid");
        }
    }

    /**
     * Get the message of a post instance
     *
     * @return message of post
     * */
    public String getMessage(){
        return this.message;
    }

    /**
     * Get the id of a post instance
     *
     * @return id of post
     * */
    public final int getId(){
        return this.id;
    }

    /**
     * Adding a comment to an original post or a reply to a comment
     *
     * @param newComment the instance of the new message that is replying to a comment/post
     * @param pointer the original post or comment being replied to
     * */
    public final void addComment(Post newComment, Post pointer){
        this.commentGraph.insert(pointer, newComment);
        pointer.increaseCommentCount();
    }

    /**
     * Increases the commentCount attribute by +1 every time a post/comment gets a reply
     * */
    public void increaseCommentCount(){
        this.commentCount += 1;
    }

    /**
     * Gets the comment graph associated with the post instance
     *
     * @return comment graph
     * */
    public final Graph getCommentGraph(){
        return this.commentGraph;
    }

    /**
     * Adds an account to the endorsement list associated with this class
     *
     * @param endorsement endoresment
     * */
    public final void addEndorsements(Endorsement endorsement){
        this.endorsementList.add(endorsement);
    }

    /**
     * Gets the current endorsed accounts of a post instance
     *
     * @return Arraylist of endorsed accounts
     * */
    public final ArrayList<Endorsement> getEndorsements(){
        return this.endorsementList;
    }

    /**
     * Returns the deleted state of the post instance
     *
     * @return boolean if its deleted or not
     * */
    public boolean isDeleted(){
        return this.isDeleted;
    }

    /**
     * Sets the post to appear as deleted
     * */
    public void delete(){

        this.isDeleted = true;
        this.account.getPosts().remove(this);
    }

    /**
     * Returns the account that is linked to this post instance
     *
     * @return Account
     * */
    public Account getAccount(){
        return this.account;
    }

    /**
     * Generates the comment reply chain linked to this post. This is generated through {@code Graph.generateGraphBuilder()}.
     *
     * See example:
     * <pre>
     * ID: 1
     * Account: bensjam
     * No. Endorsements: 0 | No. Comments: 2
     * i am c1
     * |
     * |	ID: 3
     * |	Account: bensjam
     * |	No. Endorsements: 0 | No. Comments: 0
     * |	i am c3
     * |
     * |	ID: 4
     * |	Account: bensjam
     * |	No. Endorsements: 0 | No. Comments: 1
     * |	i am c4
     * |
     * |	|	ID: 7
     * |	|	Account: bensjam
     * |	|	No. Endorsements: 0 | No. Comments: 0
     * |	|	i am c7
     * |
     * ID: 2
     * Account: bensjam
     * No. Endorsements: 0 | No. Comments: 1
     * i am c2
     * |
     * |	ID: 5
     * |	Account: bensjam
     * |	No. Endorsements: 0 | No. Comments: 0
     * |	i am c5
     * |
     * ID: 6
     * Account: bensjam
     * No. Endorsements: 0 | No. Comments: 0
     * i am c6
     * |
     *
     * </pre>
     * @apiNote This is different to {@code toString()} method
     * @return StringBuilder
     * */
    public final StringBuilder getPostChildren(){
        return this.commentGraph.generateGraphBuilder();
    }

    /**
     * Overrides Object by returning comma-seperated values
     *
     * @return String object of the current post instance
     * */
    @Override
    public String toString(){
        String output = "ID: %s,Account: %s,No. Endorsements: %s | No. Comments: %s,%s";
        return String.format(output, this.getId(), this.getAccount().getHandle(), this.getEndorsements().size(), this.commentCount, this.getMessage());

    }
}

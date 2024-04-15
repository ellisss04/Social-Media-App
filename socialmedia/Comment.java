package socialmedia;


import java.io.Serializable;

/**
 * {@code Comment()} class extends {@code Post()} class. A comment is a message that is in reply to another comment or a post.
 *
 * @author ellis mackness
 *
 * @version 1.1
 *
 * */

public class Comment extends Post implements Serializable {

    /**
     * A comment is a message that is in reply to another comment or a post.
     *
     * @param account Author of the comment
     * @param msg Message contained in the comment
     * */
    public Comment(Account account, String msg) throws InvalidPostException {
        super(account);

        // set the message of the comment
        this.setMessage(msg);
    }
}


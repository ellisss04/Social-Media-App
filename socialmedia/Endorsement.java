package socialmedia;

import java.io.Serializable;

public class Endorsement extends Post implements Serializable {
    /**
     * @param account Account instance that is linked to this post
     * @apiNote A post can be an original post or a comment
     */
    public Endorsement(Account account, String message) throws InvalidPostException {
        super(account);
        this.message = message;
        this.setMessage(this.message);
    }
}

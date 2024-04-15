package socialmedia;

import java.io.IOException;

/**
 * SocialMedia is a minimally compiling, but non-functioning implementor
 * of the MiniSocialMediaPlatform interface.
 *
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements MiniSocialMediaPlatform {

    protected static Platform currentPlatform = new Platform();

    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        Account newAccount = new Account(handle);
        currentPlatform.addAccount(newAccount);

        return newAccount.getUserId();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        for(Account acc: currentPlatform.getAccounts()){
            if(acc.getUserId() == id){
                currentPlatform.removeAccount(acc);
                for (Post post : acc.getPosts()){
                    acc.getPosts().remove(post);
                }
            }
            break;
        }
    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        boolean isAccFound = false;
        for(Account acc: currentPlatform.getAccounts()){
            if(acc.getHandle().equals(oldHandle)){
                isAccFound = true;
                acc.updateHandle(newHandle);
            }
        }
        if(!isAccFound){
            throw new HandleNotRecognisedException("Account handle doesnt exist");
        }
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        boolean isHandleFound = false;
        String output = "";

        for(Account acc: currentPlatform.getAccounts()){
            if(acc.getHandle().equals(handle)){
                isHandleFound = true;
                output = acc.toString();
                break;
            }
        }

        if(!isHandleFound){
            throw new HandleNotRecognisedException("Handle doesn't exist!");
        } else{
            return output;
        }
    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        Account acc = currentPlatform.findAccount(handle);
        acc.createPost(message);

        return acc.getPosts().peek().getId();
    }

    @Override
    public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        Account finalAcc = currentPlatform.findAccount(handle);
        Post finalPost = currentPlatform.findPost(id);

        if(finalPost instanceof Endorsement){
            throw new NotActionablePostException("Cannot action on endorsement post");
        } else{
            Endorsement newEndorsement = null;
            try {
                newEndorsement = new Endorsement(finalAcc, finalPost.getMessage());
            } catch (InvalidPostException e) {}

            finalPost.addEndorsements(newEndorsement);
            return finalPost.getId();
        }
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        Account finalAcc = currentPlatform.findAccount(handle);
        Post post = currentPlatform.findPost(id);
        Comment newComment = new Comment(finalAcc, message);
        post.addComment(newComment, post);

        return 0;
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        Post post = currentPlatform.findPost(id);
        // TODO delete endorsements
        try {
            post.setMessage("The original content was removed from the system and is no longer available.");
            post.delete();
        }
        catch (InvalidPostException e){
            System.out.println("Message too long!");
        }
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        Post post = currentPlatform.findPost(id);
        return post.toString();
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        try {
            Post post = currentPlatform.findPost(id);
            return post.getPostChildren();
        } catch (IndexOutOfBoundsException e){
            throw new NotActionablePostException("Post has no comments!");
        }
    }

    @Override
    public int getMostEndorsedPost() {
        int max_endorse = 0;
        int max_id = 0;
        for(Account acc: currentPlatform.getAccounts()){
            for(Post post: acc.getPosts()){
                int endorse_num = post.getEndorsements().size();
                if(endorse_num > max_endorse){
                    max_id = post.getId();
                    max_endorse = endorse_num;
                }
                for(Node node: post.getCommentGraph().getGraph()){
                    int comment_endorse = node.getCurrent().getEndorsements().size();
                    if(comment_endorse > max_endorse){
                        max_id = node.getCurrent().getId();
                        max_endorse = comment_endorse;
                    }
                }
            }
        }
        return max_id;
    }

    @Override
    public int getMostEndorsedAccount() {
        int highest = 0;
        int max_id = -1;
        for (Account acc: currentPlatform.getAccounts()){
            int total = 0;
            for (Post post: acc.getPosts()){
                total += post.getEndorsements().size();
            }
            if (total>=highest){
                highest = total;
                max_id = acc.getUserId();
            }
        }
        return max_id;

    }

    @Override
    public void erasePlatform() {
        currentPlatform = new Platform();
    }

    @Override
    public void savePlatform(String filename) throws IOException {
        Serialiser.serialise(filename, currentPlatform);
    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        currentPlatform = (Platform) Serialiser.deserialise(filename);
    }

}

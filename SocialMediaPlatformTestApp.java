import socialmedia.AccountIDNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.MiniSocialMediaPlatform;
import socialmedia.*;

import java.io.IOException;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws IllegalHandleException, InvalidPostException, InvalidHandleException, IOException, ClassNotFoundException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, AccountIDNotRecognisedException {
		System.out.println("The system compiled and started the execution...");

		SocialMedia sm = new SocialMedia();

		sm.createAccount("Ellis");


		System.out.println(sm.showAccount("Ellis"));

		sm.createAccount("Jack");
		sm.createPost("Jack", "this is a post");
		sm.createPost("Ellis", "this is also a post");
		sm.endorsePost("Ellis", 0);
		System.out.println(sm.getMostEndorsedPost());
		sm.commentPost("Jack", 0, "this is a comment");
		sm.commentPost("Ellis", 0, "this is also a comment");
		sm.commentPost("Jack", 4, "I am replying");


		sm.commentPost("Jack", 1, "Hi Ellis");
		System.out.println(sm.showPostChildrenDetails(0));
		System.out.println(sm.showPostChildrenDetails(1));
		System.out.println(sm.showAccount("Jack"));
		sm.removeAccount(2);
		sm.changeAccountHandle("Ellis", "Elliss");
		System.out.println(sm.showAccount("Elliss"));
		System.out.println(sm.getMostEndorsedPost());

		MiniSocialMediaPlatform platform = new SocialMedia();

//		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");


//			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";
//
//			platform.removeAccount(id);
//			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

	}
	}


package com.mock.fb.beans;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.FriendDao;
import com.mock.fb.beans.dao.GroupDao;
import com.mock.fb.beans.dao.GroupMemberDao;
import com.mock.fb.beans.dao.PostDao;
import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.Group;
import com.mock.fb.model.Post;
import com.mock.fb.model.User;

@Component
@Scope("session")
public class ProfileBean {

	@Autowired
	private AuthenticationBean auth;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private GroupMemberDao groupMemberDao;

	private String text;
	private User user;
	private List<Post> posts;
	private List<User> friends;
	private List<Group> groups;
	private List<User> waitingAcceptFriends;
	private List<Group> waitingAcceptGroups;

	@PostConstruct
	public void init() {
		user = null;
		posts = null;
		friends = null;
		groups = null;
		waitingAcceptFriends = null;
		waitingAcceptGroups = null;
	}

	public void selectUser(User user) {
		this.user = user;

		posts = postDao.getPostsByWall(user);
		friends = friendDao.getFriendsByUser(user);
		groups = groupDao.getGroupsByUser(user);
		if (auth.isLoggedIn()) {
			waitingAcceptFriends = friendDao.notFriends(auth.getUser());
			waitingAcceptGroups = groupMemberDao.notMembers(auth.getUser());
		}
	}

	public String selectUserByUserName(String userName) {
		selectUser(userDao.getUserByUsername(userName));
		return "go-user-profile";
	}

	public String friend() {
		System.out.println("Trying to add friend");
		String result = null;
		try {
			if (!friendDao.isIgnored(user, auth.getUser())) {
				System.out.println("Going to add a friend");
				friendDao.saveFriend(user, auth.getUser());
				System.out.println("Success adding a friend");
			}
			result = "friend";
		} catch (Throwable t) {
			result = "not-friended";
		}

		return result;
	}

	/**
	 *  Ignore user; faces-config: redirect added
	 */
	public String ignore() {
		friendDao.ignoreFriend(auth.getUser(), user);
		unFriend();
		return "ignore-success";

	}

	public boolean isIgnored(User user, User friend) {
		return friendDao.isIgnored(user, friend);
	}

	public String unFriend() {
		System.out.println("Trying to unfriend");
		String result = null;
		try {
			friendDao.deleteFriend(user, auth.getUser());
			/** The line below could be deleted once auto-updates began working */
			friends = friendDao.getFriendsByUser(user);
			result = "unfriended";
		} catch (Throwable t) {
			result = "not-unfriended";
		}
		return result;
	}

	public boolean isFriend() {
		return friendDao.isFriend(user, auth.getUser());
	}

	public boolean isWaiting() {
		boolean operation = friendDao.isWaiting(user, auth.getUser())
				|| friendDao.isWaiting(auth.getUser(), user);
		// return friendDao.isWaiting(user, auth.getUser());
		return operation;
	}

	public String addPost() {
		Post post = new Post(auth.getUser(), user, text);
		postDao.savePost(post);
		/** The line below could be deleted once auto-updates began working */
		posts = postDao.getPostsByWall(user);
		return "post-added-success";
	}

	public boolean thisUser() {
		return auth.getUser().getName().equals(user.getName());
	}

	public boolean userOfThePage() {
		return !(user == null);
	}

	public String acceptFriendRequest(String username) {
		System.out.println("The username is accept friend is " + username);
		friendDao.setAcceptTrue(auth.getUser(),
				userDao.getUserByUsername(username));
		/** The line below could be deleted once auto-updates began working */
		waitingAcceptFriends = friendDao.notFriends(auth.getUser());

		/**
		 * After accepting a friend request, the user that is currently logged
		 * in automatically adds that person as a friend
		 */
		friendDao.saveFriend(userDao.getUserByUsername(username),
				auth.getUser());
		friendDao.setAcceptTrue(userDao.getUserByUsername(username),
				auth.getUser());
		return "accept-success";

	}

	public String declineFriendRequest(String username) {
		friendDao.deleteFriend(auth.getUser(),
				userDao.getUserByUsername(username));
		/** The line below could be deleted once auto-updates began working */
		waitingAcceptFriends = friendDao.notFriends(auth.getUser());
		return "decline-success";
	}

	public String acceptGroupInvite(Group group) {
		groupMemberDao.setAcceptTrue(user, group);
		selectUser(auth.getUser());
		return "accept-success";
	}

	public String declineGroupInvite(Group group) {
		groupMemberDao.declineMember(user, group);
		selectUser(auth.getUser());
		return "decline-success";
	}

	public List<Group> getWaitingAcceptGroups() {
		return waitingAcceptGroups;
	}

	public void setWaitingAcceptGroups(List<Group> waitingAcceptGroups) {
		this.waitingAcceptGroups = waitingAcceptGroups;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<User> getWaitingAcceptFriends() {
		return waitingAcceptFriends;
	}

	public void setWaitingAcceptFriends(List<User> waitingAcceptFriends) {
		this.waitingAcceptFriends = waitingAcceptFriends;
	}

	public AuthenticationBean getAuth() {
		return auth;
	}

	public void setAuth(AuthenticationBean auth) {
		this.auth = auth;
	}

}

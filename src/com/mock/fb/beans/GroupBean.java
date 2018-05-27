package com.mock.fb.beans;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.GroupDao;
import com.mock.fb.beans.dao.GroupMemberDao;
import com.mock.fb.beans.dao.GroupPostDao;
import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.Group;
import com.mock.fb.model.GroupPost;
import com.mock.fb.model.Post;
import com.mock.fb.model.User;

@Component
@Scope("session")
public class GroupBean {

	@Autowired
	private AuthenticationBean auth;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private GroupPostDao groupPostDao;

	@Autowired
	private GroupMemberDao groupMemberDao;

	@Autowired
	private UserDao userDao;

	private User owner;

	private Group group;

	private List<User> members;

	private String groupName;

	private String userNameFragment;

	private List<User> searchedUsers;
	
	private List<GroupPost> posts;
	
	private String postText;

	public void createGroupPage() {
		searchedUsers = userDao.getUsersFromSearch("");
	}

	public void search() {
		searchedUsers = userDao.getUsersFromSearch(userNameFragment);
		this.searchedUsers = searchedUsers;
		this.userNameFragment = null;
	}

	public void selectGroup(String groupName) {
		group = groupDao.getGroupByGroupName(groupName);
		owner = groupDao.getOwnerByGroupName(groupName);
		posts = groupPostDao.getMessagesByGroup(group);

		members = groupMemberDao.getMembers(group);
	}

	public String createGroup() {
		groupDao.saveGroup(groupName, auth.getUser());

		group = groupDao.getGroupByGroupName(groupName);

		owner = groupDao.getOwnerByGroupName(groupName);

		members = groupMemberDao.getMembers(group);
		
		searchedUsers = userDao.getUsersFromSearch("");
		
		groupMemberDao.saveMember(owner, group);
		groupMemberDao.setAcceptTrue(owner, group);

		return "created-group";
	}
	
	public String viewGroup(String groupName) {
		selectGroup(groupName);
		return "view-group";
	}

	public boolean isOwner() {
		return owner.getName().equals(auth.getUserName());
	}

	public String inviteUser(User user) {
		System.out.println("user invited");
		groupMemberDao.saveMember(user, group);
		return "inviteGroup";
	}

	public String removeUser(User user) {
		groupMemberDao.deleteMember(user, group);
		return "removeMember";
	}
	
	public String deletegroupByGroupName(String groupName){
		groupDao.deletegroupByGroupName(groupName);
		return "delete-group";
	}

public String groupPost(){
	GroupPost groupPost = new GroupPost();
	
	groupPost.setText(postText);
	groupPost.setGroup(group);
	groupPost.setUser(auth.getUser());
	groupPostDao.savePost(groupPost);
	
	posts = groupPostDao.getMessagesByGroup(group);
	
	return "group-post";
}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserNameFragment() {
		return userNameFragment;
	}

	public void setUserNameFragment(String userNameFragment) {
		this.userNameFragment = userNameFragment;
	}

	public List<User> getSearchedUsers() {
		return searchedUsers;
	}

	public void setSearchedUsers(List<User> searchedUsers) {
		this.searchedUsers = searchedUsers;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public List<GroupPost> getPosts() {
		return posts;
	}

	public void setPosts(List<GroupPost> posts) {
		this.posts = posts;
	}
	
	
	
}

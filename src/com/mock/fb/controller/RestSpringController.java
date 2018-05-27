package com.mock.fb.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mock.fb.beans.dao.FriendDao;
import com.mock.fb.beans.dao.GroupDao;
import com.mock.fb.beans.dao.GroupMemberDao;
import com.mock.fb.beans.dao.GroupPostDao;
import com.mock.fb.beans.dao.PostDao;
import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.Group;
import com.mock.fb.model.GroupMember;
import com.mock.fb.model.GroupPost;
import com.mock.fb.model.Post;
import com.mock.fb.model.User;


@Controller
@RequestMapping("/app")
public class RestSpringController {

	private Logger log = LoggerFactory.getLogger(RestSpringController.class);
	
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private GroupPostDao groupPostDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupMemberDao groupMemberDao;
	

	/**User Dao
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public @ResponseBody User getUserByUsername(@PathVariable String username) {
		User user = userDao.getUserByUsername(username);
		log.trace("/user/{username} : {} ", user.getName() );
		return user;
	}
	

	@RequestMapping(value = "/user/POST", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Boolean addUser(@RequestBody User user) {
		try{
		userDao.saveUser(user);
		log.trace("/user/POST true : {}",  user.getName());
		return true;
		}catch (Exception e){
			log.trace("/user/POST false : {}",  user.getName());
			return false;
		}
	}
	
	@RequestMapping(value = "/search/{username}", method = RequestMethod.GET)
	public @ResponseBody UsersWrapper getUsersFromSearch(@PathVariable String username) {
		UsersWrapper usersWrapper = new UsersWrapper();
		List<User> users = userDao.getUsersFromSearch(username);
		log.trace("/search/{username}");
		log.info("List<User> users size: {}", users.size());
		usersWrapper.setList(users);
		return usersWrapper;
	}
	
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public @ResponseBody UsersWrapper getUsers(@PathVariable String username) {
		UsersWrapper usersWrapper = new UsersWrapper();
		List<User> users = userDao.getUsers(userDao.getUserByUsername(username));
		log.trace("/users/{username}");
		log.info("List<User> users size: {}", users.size());
		usersWrapper.setList(users);
		return usersWrapper;
	}
	
	/**post dao
	 * 
	 * @param username
	 * @return
	 */	
	@RequestMapping(value = "/post/{username}/posts", method = RequestMethod.GET)
	public @ResponseBody PostsWrapper getPostByUser(@PathVariable String username) {
		PostsWrapper listWrapper = new PostsWrapper();
		try{
		User user = userDao.getUserByUsername(username);
		List<Post> posts = postDao.getPostsByUser(user);
		log.trace("List<Post> posts size: {}", posts.size());
		listWrapper.setList(posts);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listWrapper;
	}
	
	
	
	@RequestMapping(value = "/post/{username}/wall", method = RequestMethod.GET)
	public @ResponseBody PostsWrapper getPostsByWall(@PathVariable String username) {
		PostsWrapper postsWrapper = new PostsWrapper();
		List<Post> posts = postDao.getPostsByUser((userDao.getUserByUsername(username)));
		log.trace("List<Post> posts size: {}", posts.size());
		postsWrapper.setList(posts);
		return postsWrapper;
	}
	
	
	
	@RequestMapping(value = "/post/{username}/post/POST", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Boolean savePost(@RequestBody Post post, @PathVariable String username) {
		try{
			post.setUserByPosterId(userDao.getUserByUsername(username));
		postDao.savePost(post);
		log.trace("/post/{username}/post/POST true: {}", post);
		return true;
		}catch (Exception e){
			log.trace("/post/{username}/post/POST true: {}", post);
			return false;
		}
	}
	
	/**
	 * group dao
	 */
	@RequestMapping(value = "/group/POST", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Boolean saveGroup(@RequestBody String groupName, @RequestBody User user){
		try{
		groupDao.saveGroup(groupName, user);
		log.trace("/group/POST true");
		return true;
		}catch (Exception e){
			log.trace("/group/POST flase");
			return false;
		}
	}
	

	@RequestMapping(value = "/group/{username}/DELETE", method = RequestMethod.DELETE)
	public @ResponseBody Boolean  deletegroupByGroupName(@PathVariable String groupName){
		try{
		groupDao.deletegroupByGroupName(groupName);
		log.trace("/group/DELETE true");
		return true;
		}catch (Exception e){
			log.trace("/group/DELETE false");
			return false;
		}
	}

	@RequestMapping(value = "/group/{groupName}", method = RequestMethod.GET)
	public @ResponseBody Group getGroupByGroupName(@PathVariable String groupName){
		log.trace("/group/{groupName}");
		return groupDao.getGroupByGroupName(groupName);
	}
	
	@RequestMapping(value = "/group/owner/{groupName}", method = RequestMethod.GET)
	public @ResponseBody User getOwnerByGroupName(@PathVariable String groupName){
		log.trace("/user/owner/{groupName}");
		return groupDao.getOwnerByGroupName(groupName);
	}
	

		@RequestMapping(value = "/groups/{username}", method = RequestMethod.GET)
		public @ResponseBody GroupsWrapper getGroupsByUser(@PathVariable String username){
			GroupsWrapper groupsWrapper = new GroupsWrapper();
			List<Group> groups = groupDao.getGroupsByUser(userDao.getUserByUsername(username));
			groupsWrapper.setList(groups);
			log.trace("/groups/{username}");
			return groupsWrapper;
		}
	
	/**
	 * group post dao
	 */
	@RequestMapping(value = "/groupPost/POST", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Boolean savePost(@RequestBody GroupPost groupPost){
		try{
		groupPostDao.savePost(groupPost);
		log.trace("/groupPost/POST true");
		return true;
		}catch (Exception e){
			log.trace("/groupPost/POST false");
			return false;
		}
	}
	
	@RequestMapping(value = "/groupPost/{group}", method = RequestMethod.GET)
	public @ResponseBody GroupPostsWrapper getMessagesByGroup(@PathVariable Group group) {
		GroupPostsWrapper groupPostsWrapper = new GroupPostsWrapper();
		List<GroupPost> posts = groupPostDao.getMessagesByGroup(group);
		log.trace("/groupPost/{group}: {}", posts.size());
		groupPostsWrapper.setList(posts);
		return groupPostsWrapper;
	}
	
	/**friends dao
	 * 
	 * @param friend
	 * @param username
	 */
	@RequestMapping(value = "/user/{username}/{friend}/POST", method = RequestMethod.POST)
	public @ResponseBody Boolean saveFriend(@PathVariable User friend, @PathVariable String username) {
		try{
			friendDao.saveFriend((userDao.getUserByUsername(username)), friend);
			log.trace("s/user/{username}/{friend}/POST  :  {}", username +friend.getName());
			return true;
		} catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * Accept the {friend}'s friend request
	 * 
	 * friends dao
	 * @param friend
	 * @param username
	 */
	@RequestMapping(value = "/user/{username}/{friend}/accept", method = RequestMethod.POST)
	public @ResponseBody Boolean acceptFriend(@PathVariable User friend, @PathVariable String username) {
		try{
			friendDao.setAcceptTrue(userDao.getUserByUsername(username), friend);
			log.debug("/user/{username}/{friend}/accept  :  {}", username +friend.getName());
			return true;
		} catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * Delete the {friend} from this {username}'s friends list
	 * 
	 * friends dao
	 * @param friend
	 * @param username
	 */
	@RequestMapping(value = "/user/{username}/{friend}/delete", method = RequestMethod.POST)
	public @ResponseBody Boolean deleteFriend(@PathVariable User friend, @PathVariable String username) {
		try{
			friendDao.deleteFriend(userDao.getUserByUsername(username), friend);
			log.debug("/user/{username}/{friend}/delete  :  {}", username +friend.getName());
			return true;
		} catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Decline the {friend} from this {username}'s friends list
	 * 
	 * friends dao
	 * @param friend
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/user/{username}/{friend}/decline", method = RequestMethod.POST)
	public @ResponseBody Boolean declineFriend(@PathVariable User friend, @PathVariable String username) {
		try{
			friendDao.deleteFriend(userDao.getUserByUsername(username), friend);
			log.debug("/user/{username}/{friend}/decline  :  {}", username +friend.getName());
			return true;
		} catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * Return the user's friends as a List<User> 
	 * 
	 * friends dao
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/user/{username}/friends", method = RequestMethod.GET)
	public @ResponseBody UsersWrapper getFriendsByUsername(@PathVariable String username) {
		List<User> friends = friendDao.getFriendsByUser(userDao.getUserByUsername(username));
		log.debug("List<User> friends size: {}", friends.size());
		UsersWrapper listWrappers = new UsersWrapper();
		listWrappers.setList(friends);
		return listWrappers;
	}
	
	/**
	 * Return true if is friend
	 * 
	 * friends dao
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/user/{username}/{friend}/isFriend", method = RequestMethod.POST)
	public @ResponseBody Boolean isFriend(@PathVariable User friend,@PathVariable String username) {
		try{
			friendDao.isFriend(userDao.getUserByUsername(username), friend);
			return true;
		} catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Return true if is waiting
	 * 
	 * friends dao
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/user/{username}/{friend}/isWaiting", method = RequestMethod.POST)
	public @ResponseBody Boolean isWaiting(@PathVariable User friend,@PathVariable String username) {
		try{
			friendDao.isWaiting(userDao.getUserByUsername(username), friend);
			return true;
		} catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Return a List<User> that has sent a friend request to this user{username} 
	 * 
	 * friends dao
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/user/{username}/friendRequests", method = RequestMethod.GET)
	public @ResponseBody UsersWrapper getFriendRequestsByUsername(@PathVariable String username) {
		List<User> friendRequests = friendDao.notFriends(userDao.getUserByUsername(username));
		log.debug("List<User> friend requests size: {}", friendRequests.size());
		UsersWrapper listWrappers = new UsersWrapper();
		listWrappers.setList(friendRequests);
		return listWrappers;
	}
	
	/**
	 * Save a member(type user) to the group
	 * 
	 * GroupMemberDao
	 * @param username
	 * @param group
	 * 
	 */
	@RequestMapping(value = "/group-member/{username}/{group}/POST", method = RequestMethod.POST)
	public @ResponseBody Boolean addMember(@PathVariable String username, @PathVariable Group group) {
		try{
			groupMemberDao.saveMember(userDao.getUserByUsername(username), group);
			log.trace("/group-member/{username}/{group}/POST true : {}",  username);
			return true;
		}catch (Exception e){
			log.trace("/group-member/{username}/{group}/POST false : {}",  username);
			return false;
		}
	}

	/**
	 * Delete a member(type user) from the group
	 * 
	 * GroupMemberDao
	 * @param username
	 * @param group
	 * 
	 */
	@RequestMapping(value = "/group-member/{username}/{group}/delete", method = RequestMethod.POST)
	public @ResponseBody Boolean deleteMember(@PathVariable String username, @PathVariable Group group) {
		try{
			groupMemberDao.deleteMember(userDao.getUserByUsername(username), group);
			log.trace("/group-member/{username}/{group}/delete true : {}",  username);
			return true;
		}catch (Exception e){
			log.trace("/group-member/{username}/{group}/delete false : {}",  username);
			return false;
		}
	}
	
	/**
	 * Accept the user 
	 * 
	 * GroupMemberDao
	 *@param username
	 * @param group
	 * 
	 */
	@RequestMapping(value = "/group-member/{username}/{group}/accept", method = RequestMethod.POST)
	public @ResponseBody Boolean acceptMember(@PathVariable String username,  @PathVariable Group group) {
		try{
			groupMemberDao.setAcceptTrue(userDao.getUserByUsername(username), group);
			log.debug("/group-member/{username}/{group}/accept  :  {}", username);
			return true;
		} catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Return a list of members 
	 * 
	 * GroupMemberDao
	 * @param group
	 * 
	 */
	@RequestMapping(value = "/group-member/{username}/friendRequests", method = RequestMethod.GET)
	public @ResponseBody UsersWrapper getMembersByGroup(@PathVariable Group group) {
		List<User> groupMembersList = groupMemberDao.getMembers(group);
		log.debug("List<User> group members size: {}", groupMembersList.size());
		UsersWrapper listWrappers = new UsersWrapper();
		listWrappers.setList(groupMembersList);
		return listWrappers;
	}
	
	/**
	 * Return a list of group invitations
	 * 
	 * GroupMemberDao
	 * @param group
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/group-member/{username}/{group}/friendRequests", method = RequestMethod.GET)
	public @ResponseBody GroupsWrapper getGroupInviteList(@PathVariable String username, @PathVariable Group group) {
		List<Group> groupIviteList = groupMemberDao.notMembers(userDao.getUserByUsername(username));
		log.debug("List<User> group members size: {}", groupIviteList.size());
		GroupsWrapper listWrappers = new GroupsWrapper();
		listWrappers.setList(groupIviteList);
		return listWrappers;
	}
	
	/**
	 * Return true if is waiting
	 * 
	 * GroupMemberDao
	 * @param Group
	 * @param username
	 * 
	 */
	@RequestMapping(value = "/user/{username}/{group}/isWaiting", method = RequestMethod.POST)
	public @ResponseBody Boolean isWaitingGroup(@PathVariable Group group,@PathVariable String username) {
		try{
			groupMemberDao.isWaiting(userDao.getUserByUsername(username), group);
			return true;
		} catch(Exception e){
			return false;
		}
	}

}
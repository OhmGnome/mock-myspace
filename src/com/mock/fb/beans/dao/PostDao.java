package com.mock.fb.beans.dao;

import java.util.List;

import com.mock.fb.model.Post;
import com.mock.fb.model.User;

public interface PostDao {
	public List<Post> getPostsByUser(User user);
	public List<Post> getPostsByWall(User user);
	public void savePost(Post post);
}

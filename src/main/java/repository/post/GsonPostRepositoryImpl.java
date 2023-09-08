package repository.post;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import model.Post;
import model.PostStatus;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.*;

@AllArgsConstructor
public class GsonPostRepositoryImpl implements PostRepository {

    Gson gson;
    String fileName;

    @Override
    public Post getById(String id) throws IOException {
        return getByIdFromList(getListFromFile(gson, fileName, Post.class), id);
    }

    @Override
    public List<Post> getAllByWriterId(String writerId) throws IOException {
        return getListFromFile(gson, fileName, Post.class).stream()
                .filter(writer -> writer.getId().equals(writerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getAll() throws IOException {
        return getListFromFile(gson, fileName, Post.class);
    }

    @Override
    public Post save(Post post) throws IOException {
        List<Post> postList = getListFromFile(gson, fileName, Post.class);
        post.setId(generateNewId());
        postList.add(post);
        saveToFile(postList, gson, fileName);
        return post;
    }

    @Override
    public Post update(Post updatedPost) throws IOException {
        List<Post> writerList = updateEntityInList(getListFromFile(gson, fileName, Post.class), updatedPost);
        saveToFile(writerList, gson, fileName);
        return updatedPost;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Post> postList = getListFromFile(gson, fileName, Post.class).stream()
                .peek(post -> {
                    if (post.getId().equals(id)) {
                        post.setPostStatus(PostStatus.DELETED);
                    }
                }).collect(Collectors.toList());
        saveToFile(postList, gson, fileName);
    }
}
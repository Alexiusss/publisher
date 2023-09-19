package repository.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Post;
import model.PostStatus;
import util.LocalDateAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.*;

public class GsonPostRepositoryImpl implements PostRepository {

    Gson gson;
    String fileName;

    public GsonPostRepositoryImpl(String fileName) {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                // https://stackoverflow.com/a/53246168
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .setPrettyPrinting()
                .create();
        this.fileName = fileName;
    }

    @Override
    public Post getById(String id) throws IOException {
        return getByIdFromList(getListFromFile(gson, fileName, Post.class), id);
    }

    @Override
    public List<Post> getAllByWriterId(String writerId) throws IOException {
        return getListFromFile(gson, fileName, Post.class).stream()
                .filter(post -> post.getWriterId().equals(writerId))
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
        post.setCreated(LocalDate.now());
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
package repository.post;

import model.Post;

import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {

    @Override
    public Post getById(String id) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        return List.of();
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void deleteById(String id) {
    }
}
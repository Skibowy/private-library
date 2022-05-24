package Library.repo;

import Library.entity.Book;
import Library.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByState(State state);
}

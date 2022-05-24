package Library;

import Library.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class LibraryApi {

    private LibraryManager books;


    public LibraryApi(LibraryManager books) {
        this.books = books;
    }

    @GetMapping("/all")
    public Iterable<Book> getAll() {
        return books.findAll();
    }

    @GetMapping
    public Optional<Book> getById(@RequestParam Long index) {
        return books.findById(index);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return books.save(book);
    }

//    @PutMapping
//    public Book updateBook(@RequestParam Long index) {
//        return books.save(book);
//    }

    @DeleteMapping
    public void deleteBook(@RequestParam Long index) {
        books.deleteById(index);
    }
}

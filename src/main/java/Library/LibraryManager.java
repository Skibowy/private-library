package Library;

import Library.entity.Book;
import Library.entity.Genre;
import Library.entity.State;
import Library.repo.LibraryRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryManager {

    private final LibraryRepository libraryRepository;

    public LibraryManager(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Optional<Book> findById(Long id) {
        return libraryRepository.findById(id);
    }

    public Iterable<Book> findAll() {
        return libraryRepository.findAll();
    }

    public Book save(Book book) {
        return libraryRepository.save(book);
    }

    public void deleteById(Long id) {
        libraryRepository.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        save(new Book(1L, "Malowany czlowiek I", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(2L, "Malowany czlowiek II", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(3L, "Zmierzch", "Stephenie Meyer", "Zmierzch", State.WYPOZYCZONA, Genre.Romans));
        save(new Book(4L, "Pustynna włócznia I", "Peter U. Brett", "Cykl Demoniczny", State.WYPOZYCZONA, Genre.Fantastyka));
        save(new Book(5L, "Pustynna włócznia II", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(6L, "Wojna w blasku dnia I", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(7L, "Księżyc w nowiu", "Stephenie Meyer", "Zmierzch", State.NA_STANIE, Genre.Romans));
        save(new Book(8L, "Zaćmienie", "Stephenie Meyer", "Zmierzch", State.WYPOZYCZONA, Genre.Romans));
        save(new Book(9L, "Wojna w blasku dnia II", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(10L, "Tron z czaszek I", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(11L, "Tron z czaszek II", "Peter U. Brett", "Cykl Demoniczny", State.WYPOZYCZONA, Genre.Fantastyka));
        save(new Book(12L, "Otchłań", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(13L, "Otchłań II", "Peter U. Brett", "Cykl Demoniczny", State.NA_STANIE, Genre.Fantastyka));
        save(new Book(14L, "Przed świtem", "Stephenie Meyer", "Zmierzch", State.NA_STANIE, Genre.Romans));
    }

}

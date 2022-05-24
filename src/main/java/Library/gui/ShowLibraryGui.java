package Library.gui;

import Library.entity.Book;
import Library.entity.State;
import Library.repo.LibraryRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
@Route("library")
public class ShowLibraryGui extends VerticalLayout {

    private final LibraryRepository libraryRepository;

    @Autowired
    public void ShowLibraryGui(){
        Button buttonMainMenu = new Button("Powrót do menu głównego");
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("menu")));

        Text textBooks = new Text("Zawartość biblioteki");

        List<Book> bookList = libraryRepository.findAll();
        List<Book> borrowedBooksList = libraryRepository.findAllByState(State.Wypożyczona);
        List<Book> availableBooksList = libraryRepository.findAllByState(State.Na_stanie);
        Grid<Book> library = new Grid<>(Book.class);
        Grid<Book> borrowed = new Grid<>(Book.class);
        Grid<Book> available = new Grid<>(Book.class);
        library.setItems(bookList);
        library.removeAllColumns();
        borrowed.setItems(borrowedBooksList);
        borrowed.removeAllColumns();
        borrowed.setVisible(false);
        available.setItems(availableBooksList);
        available.removeAllColumns();
        available.setVisible(false);

        library.addColumn(Book::getId).setHeader("#").setSortable(true);
        library.addColumn(Book::getTitle).setHeader("Tytuł").setSortable(true);
        library.addColumn(Book::getAuthor).setHeader("Autor").setSortable(true);
        library.addColumn(Book::getSeries).setHeader("Seria").setSortable(true);
        library.addColumn(Book::getGenre).setHeader("Gatunek").setSortable(true);
        library.addColumn(Book::getState).setHeader("Stan").setSortable(true);

        library.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        borrowed.addColumn(Book::getId).setHeader("#").setSortable(true);
        borrowed.addColumn(Book::getTitle).setHeader("Tytuł").setSortable(true);
        borrowed.addColumn(Book::getAuthor).setHeader("Autor").setSortable(true);
        borrowed.addColumn(Book::getSeries).setHeader("Seria").setSortable(true);
        borrowed.addColumn(Book::getGenre).setHeader("Gatunek").setSortable(true);

        borrowed.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        available.addColumn(Book::getId).setHeader("#").setSortable(true);
        available.addColumn(Book::getTitle).setHeader("Tytuł").setSortable(true);
        available.addColumn(Book::getAuthor).setHeader("Autor").setSortable(true);
        available.addColumn(Book::getSeries).setHeader("Seria").setSortable(true);
        available.addColumn(Book::getGenre).setHeader("Gatunek").setSortable(true);

        available.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        Button buttonShowAll = new Button("Pokaż wszystkie książki");
        buttonShowAll.addClickListener(ClickEvent -> {
            library.setVisible(true);
            borrowed.setVisible(false);
            available.setVisible(false);
        });

        Button buttonShowBorrowed = new Button("Pokaż tylko wypożyczone");
        buttonShowBorrowed.addClickListener(ClickEvent -> {
            library.setVisible(false);
            borrowed.setVisible(true);
            available.setVisible(false);
        });

        Button buttonShowAvailable = new Button("Pokaż tylko dostępne");
        buttonShowAvailable.addClickListener(ClickEvent -> {
            library.setVisible(false);
            borrowed.setVisible(false);
            available.setVisible(true);
        });

        Dialog dialogDeletedBook = new Dialog();
        dialogDeletedBook.setCloseOnEsc(false);
        dialogDeletedBook.setCloseOnOutsideClick(false);
        Span messageOK = new Span("Usunięto ksiazke!");
        Button confirmButton = new Button("OK!", event -> {
            UI.getCurrent().getPage().reload();
            dialogDeletedBook.close();
        });
        dialogDeletedBook.add(messageOK, confirmButton);

        TextField textFieldDeleteBook = new TextField("Wpisz ID książki, którą chcesz usunąć");
        textFieldDeleteBook.setWidth("300px");

        Button buttonDeleteWorker = new Button("Usuń książkę!");
        buttonDeleteWorker.addClickListener(ClickEvent -> {
            deleteBook(textFieldDeleteBook.getValue());
            textFieldDeleteBook.clear();
            dialogDeletedBook.open();
        });

        add(buttonMainMenu, textBooks, library, available, borrowed, buttonShowAll, buttonShowAvailable, buttonShowBorrowed, buttonShowAvailable, textFieldDeleteBook, buttonDeleteWorker, dialogDeletedBook);
    }

    public void deleteBook(String id){
        libraryRepository.deleteById(Long.parseLong(id));
    }
}

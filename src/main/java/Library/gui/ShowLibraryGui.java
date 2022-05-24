package Library.gui;

import Library.entity.Book;
import Library.entity.Genre;
import Library.entity.State;
import Library.repo.LibraryRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
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
        List<Book> borrowedBooksList = libraryRepository.findAllByState(State.WYPOZYCZONA);
        List<Book> availableBooksList = libraryRepository.findAllByState(State.NA_STANIE);
        Grid<Book> libraryGrid = new Grid<>(Book.class);
        libraryGrid.setItems(bookList);
        libraryGrid.removeAllColumns();

        libraryGrid.addColumn(Book::getId).setHeader("#").setSortable(true);
        libraryGrid.addColumn(Book::getTitle).setHeader("Tytuł").setSortable(true);
        libraryGrid.addColumn(Book::getAuthor).setHeader("Autor").setSortable(true);
        libraryGrid.addColumn(Book::getSeries).setHeader("Seria").setSortable(true);
        libraryGrid.addColumn(Book::getGenre).setHeader("Gatunek").setSortable(true);
        libraryGrid.addColumn(Book::getState).setHeader("Stan").setSortable(true).setKey("State");

        libraryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        Button buttonShowAll = new Button("Pokaż wszystkie książki");
        buttonShowAll.addClickListener(ClickEvent -> {
            libraryGrid.getColumnByKey("State").setVisible(true);
            libraryGrid.setItems(bookList);
        });

        Button buttonShowBorrowed = new Button("Pokaż tylko wypożyczone");
        buttonShowBorrowed.addClickListener(ClickEvent -> {
            libraryGrid.getColumnByKey("State").setVisible(false);
            libraryGrid.setItems(borrowedBooksList);
        });

        Button buttonShowAvailable = new Button("Pokaż tylko dostępne");
        buttonShowAvailable.addClickListener(ClickEvent -> {
            libraryGrid.getColumnByKey("State").setVisible(false);
            libraryGrid.setItems(availableBooksList);
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

        add(buttonMainMenu, textBooks, libraryGrid, buttonShowAll, buttonShowAvailable, buttonShowBorrowed, buttonShowAvailable);
        changeState();
        add(textFieldDeleteBook, buttonDeleteWorker, dialogDeletedBook);
    }

    private void changeState(){
        TextField textFieldChangeState = new TextField("Wpisz ID książki, której status chcesz zmienić");
        textFieldChangeState.setWidth("400px");

        ComboBox<State> stateComboBox = new ComboBox<>("Wybierz status, który chcesz ustawić");
        stateComboBox.setItems(State.NA_STANIE, State.WYPOZYCZONA, State.INNY);

        Button buttonChangeState = new Button("Zmień status!");
        buttonChangeState.addClickListener(ClickEvent -> {
            updateState(Long.valueOf(textFieldChangeState.getValue()), stateComboBox.getValue());
            textFieldChangeState.clear();
            stateComboBox.clear();
            UI.getCurrent().getPage().reload();
        });
        add(textFieldChangeState, stateComboBox, buttonChangeState);
    }

    @Transactional
    private void updateState(Long id, State state) {
        Book book = libraryRepository.getById(id);
        book.setState(state);
        libraryRepository.save(book);
    }

    private void deleteBook(String id){
        libraryRepository.deleteById(Long.parseLong(id));
    }
}

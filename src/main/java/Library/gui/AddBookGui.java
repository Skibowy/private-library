package Library.gui;

import Library.entity.Book;
import Library.entity.Genre;
import Library.entity.State;
import Library.repo.LibraryRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Slf4j
@Route("add-book")
public class AddBookGui extends VerticalLayout {

    private final LibraryRepository libraryRepository;

    @Autowired
    public void AddBookGui(){
        Text textAdd = new Text("Wprowadź kolejno dane książki");
        TextField textFieldTitle = new TextField("Tytuł:");
        TextField textFieldAuthor = new TextField("Autor:");
        TextField textFieldSeries = new TextField("Seria:");
        ComboBox<Genre> comboBoxGenre = new ComboBox<>();
        ComboBox<State> comboBoxState = new ComboBox<>();

        Dialog dialogAddedBook = new Dialog();
        dialogAddedBook.setCloseOnEsc(false);
        dialogAddedBook.setCloseOnOutsideClick(true);
        Span messageOK = new Span("Dodano nową książkę");
        Button confirmButton = new Button("OK!", event -> dialogAddedBook.close());
        dialogAddedBook.add(messageOK, confirmButton);


        comboBoxGenre.setItems(Genre.Fantastyka, Genre.Romans, Genre.Inny);
        comboBoxGenre.setLabel("Wybierz gatunek książki");
        comboBoxState.setItems(State.NA_STANIE, State.WYPOZYCZONA, State.INNY);
        comboBoxState.setLabel("Wybierz stan książki");

        Button buttonMainMenu = new Button("Powrót do menu głównego");
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("menu")));

        Button buttonSaveBook = new Button("Dodaj książkę", new Icon(VaadinIcon.THUMBS_UP));
        buttonSaveBook.setIconAfterText(true);
        buttonSaveBook.addClickListener(ClickEvent -> {
            addBook(textFieldTitle.getValue(), textFieldAuthor.getValue(), textFieldSeries.getValue(), comboBoxState.getValue(), comboBoxGenre.getValue());
            textFieldTitle.clear();
            textFieldAuthor.clear();
            textFieldSeries.clear();
            comboBoxGenre.clear();
            comboBoxState.clear();
            dialogAddedBook.open();
        });

        add(textAdd, textFieldTitle, textFieldAuthor,
                textFieldSeries, comboBoxGenre, comboBoxState, buttonSaveBook,
                buttonMainMenu, dialogAddedBook);
    }

    public void addBook(String title, String author, String series, State state, Genre genre) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setSeries(series);
        book.setGenre(genre);
        book.setState(state);
        libraryRepository.save(book);
        log.info("Dodano nową książkę! {} {}", title, author);
    }

}

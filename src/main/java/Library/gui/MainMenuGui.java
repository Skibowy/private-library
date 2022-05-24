package Library.gui;

import Library.repo.LibraryRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

@Route("menu")
public class MainMenuGui extends VerticalLayout {

    @Autowired
    public MainMenuGui(LibraryRepository libraryRepository){

        Text textHello = new Text("Witaj w Twojej prywatnej bibliotece! Wybierz opcję z menu poniżej:");
        Button buttonAddBook = new Button("Dodaj książkę", new Icon(VaadinIcon.ARROW_RIGHT));
        buttonAddBook.setIconAfterText(true);
        buttonAddBook.addClickListener(e->buttonAddBook.getUI().ifPresent(ui->ui.navigate("add-book")));

        Button buttonShowLibrary = new Button("Pokaż całą bibliotekę", new Icon(VaadinIcon.ARROW_RIGHT));
        buttonShowLibrary.setIconAfterText(true);
        buttonShowLibrary.addClickListener(e->buttonShowLibrary.getUI().ifPresent(ui -> ui.navigate("library")));

        add(textHello, buttonAddBook, buttonShowLibrary);
    }

}

package pl.krzysztof.drzazga.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import pl.krzysztof.drzazga.data.LecturesRepository;
import pl.krzysztof.drzazga.model.ConferencePath;
import pl.krzysztof.drzazga.model.Lecture;

import java.util.List;

@SpringComponent
@Scope("prototype")
public class ConferenceSchedule extends HorizontalLayout implements View {

    private VerticalLayout root;

    @Value("${mainPage.header}")
    private String header;

    private LecturesRepository lecturesRepository;

    @Autowired
    public ConferenceSchedule(LecturesRepository repository) {
        this.lecturesRepository = repository;
    }

    private void init() {
        this.initializeLayout();
        this.createHeader();
        this.createLecturesTable();
    }

    private void createLecturesTable() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        for (ConferencePath path : ConferencePath.values()) {
            List<Lecture> lectures = lecturesRepository.getAllByConferencePathOrderByLectureDateAsc(path);
            LecturesLayout lecturesTable = new LecturesLayout();
            lecturesTable.addHeader(path.toString());
            lecturesTable.createLayout(lectures);
            horizontalLayout.addComponent(lecturesTable);
        }
        this.root.addComponent(horizontalLayout);
    }


    private void initializeLayout() {
        this.root = new VerticalLayout();
        this.root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(root);
    }

    private void createHeader() {
        Label header = new Label(this.header);
        header.addStyleName(ValoTheme.LABEL_H1);
        root.addComponent(header);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.removeAllComponents();
        this.init();
    }
}

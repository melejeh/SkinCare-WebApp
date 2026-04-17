package PlanetMel.views;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("dummy")
public class DummyView extends VerticalLayout {

    public DummyView() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("background", "white");
        getStyle().set("font-family", "serif");

        H1 portalTitle = new H1("iSky Airline Portal");
        portalTitle.getStyle()
                .set("font-size", "30px")
                .set("margin", "0")
                .set("font-weight", "700");

        Anchor booking = new Anchor("/", "Booking");
        Anchor queryFlight = new Anchor("/dummy", "Query Flight Status");
        Anchor queryTicket = new Anchor("/dummy", "Query Ticket Status");
        Anchor confirmTicket = new Anchor("/dummy", "Confirm Passenger Ticket");

        HorizontalLayout links = new HorizontalLayout(booking, queryFlight, queryTicket, confirmTicket);
        links.setSpacing(true);
        links.getStyle().set("font-size", "14px");

        HorizontalLayout headerBar = new HorizontalLayout(portalTitle, links);
        headerBar.setWidthFull();
        headerBar.expand(portalTitle);
        headerBar.setAlignItems(Alignment.CENTER);
        headerBar.getStyle()
                .set("background-color", "#efefef")
                .set("padding", "18px 22px");

        Paragraph message = new Paragraph("Coming soon…");
        message.getStyle()
                .set("font-size", "18px")
                .set("margin", "24px 32px");

        add(headerBar, message);
    }
}

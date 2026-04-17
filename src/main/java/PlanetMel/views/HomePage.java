package PlanetMel.views;

import PlanetMel.domain.Product;
import PlanetMel.service.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.html.Image;



import java.util.List;

@Route("")
@PageTitle("PlanetMel Skin Co.")
public class HomePage extends VerticalLayout {

    private final ProductService productService;

    public HomePage(ProductService productService) {
        this.productService = productService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        getStyle().set("background-color", "#fdf6f0");

        add(buildHeader());
        add(buildHero());
        add(buildProductGrid());
        add(buildFooter());
    }

    private com.vaadin.flow.component.Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.getStyle()
                .set("background-color", "#5c4033")
                .set("padding", "16px 32px");

        Span brand = new Span("✦  PLANETMEL SKIN CO.  ✦");
        brand.getStyle()
                .set("color", "#e8d5c4")
                .set("font-family", "Georgia, serif")
                .set("font-size", "18px")
                .set("font-weight", "bold")
                .set("letter-spacing", "3px");

        header.add(brand);
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        return header;
    }

    private com.vaadin.flow.component.Component buildHero() {
        VerticalLayout hero = new VerticalLayout();
        hero.setAlignItems(Alignment.CENTER);
        hero.setPadding(true);
        hero.getStyle()
                .set("background-color", "#fffaf7")
                .set("padding", "48px 32px")
                .set("border-bottom", "1px solid #e8d5c4");

        Span star = new Span("✦");
        star.getStyle().set("font-size", "32px").set("color", "#c9a98a");

        H1 title = new H1("Skincare that's out of this world.");
        title.getStyle()
                .set("font-family", "Georgia, serif")
                .set("color", "#5c4033")
                .set("font-size", "32px")
                .set("margin", "8px 0");

        Paragraph subtitle = new Paragraph(
                "Skincare made simple. Thoughtfully formulated to hydrate, restore, and support your natural glow.");
        subtitle.getStyle()
                .set("color", "#a08878")
                .set("font-family", "Georgia, serif")
                .set("font-style", "italic")
                .set("max-width", "500px")
                .set("text-align", "center");

        Button shopBtn = new Button("Shop Now");
        shopBtn.getStyle()
                .set("background-color", "#c9a98a")
                .set("color", "white")
                .set("font-family", "Georgia, serif")
                .set("border-radius", "6px")
                .set("padding", "10px 28px")
                .set("cursor", "pointer");
        shopBtn.addClickListener(e ->
                UI.getCurrent().getPage().executeJs("document.getElementById('products').scrollIntoView({behavior:'smooth'})"));

        hero.add(star, title, subtitle, shopBtn);
        return hero;
    }

    private com.vaadin.flow.component.Component buildProductGrid() {
        VerticalLayout section = new VerticalLayout();
        section.setAlignItems(Alignment.CENTER);
        section.getStyle()
                .set("padding", "48px 32px")
                .set("background-color", "#fdf6f0");
        section.getElement().setAttribute("id", "products");

        H2 heading = new H2("Our Products");
        heading.getStyle()
                .set("font-family", "Georgia, serif")
                .set("color", "#5c4033")
                .set("margin-bottom", "8px");

        Paragraph sub = new Paragraph("Carefully crafted for every skin type.");
        sub.getStyle().set("color", "#b89c8a").set("font-family", "Georgia, serif").set("font-style", "italic");

        section.add(heading, sub);

        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            Paragraph empty = new Paragraph("No products available yet. Check back soon!");
            empty.getStyle().set("color", "#a08878").set("font-family", "Georgia, serif");
            section.add(empty);
        } else {
            HorizontalLayout grid = new HorizontalLayout();
            grid.getStyle().set("flex-wrap", "wrap");
            grid.setJustifyContentMode(JustifyContentMode.CENTER);
            grid.getStyle().set("gap", "20px").set("max-width", "1000px");

            for (Product p : products) {
                grid.add(buildProductCard(p));
            }
            section.add(grid);
        }

        return section;
    }

    private com.vaadin.flow.component.Component buildProductCard(Product product) {
        VerticalLayout card = new VerticalLayout();
        card.setAlignItems(Alignment.CENTER);
        card.setSpacing(false);
        card.getStyle()
                .set("background-color", "#fffaf7")
                .set("border", "1px solid #e8d5c4")
                .set("border-radius", "12px")
                .set("padding", "24px")
                .set("width", "220px")
                .set("cursor", "pointer")
                .set("transition", "box-shadow 0.2s");

        Span category = new Span(product.getCategory());
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            Image img = new Image(product.getImageUrl(), product.getName());
            img.setWidth("160px");
            img.setHeight("160px");
            img.getStyle().set("object-fit", "cover").set("border-radius", "8px");
            card.add(img);
        }
        category.getStyle()
                .set("font-size", "11px")
                .set("color", "#b89c8a")
                .set("font-family", "Georgia, serif")
                .set("letter-spacing", "2px")
                .set("text-transform", "uppercase");

        H3 name = new H3(product.getName());
        name.getStyle()
                .set("font-family", "Georgia, serif")
                .set("color", "#5c4033")
                .set("font-size", "16px")
                .set("margin", "8px 0 4px 0")
                .set("text-align", "center");

        Paragraph desc = new Paragraph(product.getDescription() != null ? product.getDescription() : "");
        desc.getStyle()
                .set("font-size", "12px")
                .set("color", "#a08878")
                .set("font-family", "Georgia, serif")
                .set("text-align", "center")
                .set("margin", "0 0 12px 0");

        Span price = new Span("$" + String.format("%.2f", product.getPrice()));
        price.getStyle()
                .set("font-family", "Georgia, serif")
                .set("color", "#c9a98a")
                .set("font-size", "16px")
                .set("font-weight", "bold");

        Button orderBtn = new Button("Order Now");
        orderBtn.getStyle()
                .set("background-color", "#c9a98a")
                .set("color", "white")
                .set("font-family", "Georgia, serif")
                .set("border-radius", "6px")
                .set("margin-top", "12px")
                .set("cursor", "pointer");
        orderBtn.addClickListener(e ->
                orderBtn.getUI().ifPresent(ui ->
                        ui.navigate(OrderView.class, product.getId())));

        card.add(category, name, desc, price, orderBtn);
        return card;
    }

    private com.vaadin.flow.component.Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setJustifyContentMode(JustifyContentMode.CENTER);
        footer.getStyle()
                .set("background-color", "#5c4033")
                .set("padding", "20px");

        Span text = new Span("© 2025 PlanetMel Skin Co. — Skincare that's out of this world.");
        text.getStyle()
                .set("color", "#e8d5c4")
                .set("font-family", "Georgia, serif")
                .set("font-size", "12px")
                .set("font-style", "italic");

        footer.add(text);
        return footer;
    }
}
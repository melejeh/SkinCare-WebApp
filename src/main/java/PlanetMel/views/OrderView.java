package PlanetMel.views;

import PlanetMel.domain.Customer;
import PlanetMel.domain.Order;
import PlanetMel.domain.Product;
import PlanetMel.service.CustomerService;
import PlanetMel.service.OrderService;
import PlanetMel.service.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@Route("order")
@PageTitle("Place Order — PlanetMel")
public class OrderView extends VerticalLayout implements HasUrlParameter<Long> {

    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;

    private Product selectedProduct;

    private TextField nameField;
    private EmailField emailField;
    private IntegerField quantityField;
    private Span productInfo;
    private Span statusLabel;
    private TextField addressField;


    public OrderView(ProductService productService, CustomerService customerService, OrderService orderService) {
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        getStyle().set("background-color", "#fdf6f0");

        add(buildHeader());
    }

    @Override
    public void setParameter(BeforeEvent event, Long productId) {
        selectedProduct = productService.getProductById(productId);
        if (selectedProduct == null) {
            add(new Paragraph("Product not found."));
            return;
        }
        add(buildOrderForm());
        add(buildFooter());
    }

    private com.vaadin.flow.component.Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.CENTER);
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
        return header;
    }

    private com.vaadin.flow.component.Component buildOrderForm() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);
        layout.getStyle().set("padding", "48px 32px");

        VerticalLayout card = new VerticalLayout();
        card.setAlignItems(Alignment.START);
        card.getStyle()
                .set("background-color", "#fffaf7")
                .set("border", "1px solid #e8d5c4")
                .set("border-radius", "16px")
                .set("padding", "32px")
                .set("max-width", "480px")
                .set("width", "100%");

        Span star = new Span("✦");
        star.getStyle().set("font-size", "22px").set("color", "#c9a98a");

        H2 title = new H2("Place Your Order");
        title.getStyle()
                .set("font-family", "Georgia, serif")
                .set("color", "#5c4033")
                .set("margin", "4px 0 16px 0");

        productInfo = new Span("Ordering: " + selectedProduct.getName() + " — $" + String.format("%.2f", selectedProduct.getPrice()));
        productInfo.getStyle()
                .set("font-family", "Georgia, serif")
                .set("color", "#c9a98a")
                .set("font-style", "italic")
                .set("margin-bottom", "16px");

        nameField = new TextField(" Full Name");
        nameField.setWidthFull();
        styleField(nameField);

        emailField = new EmailField("Email Address");
        emailField.setWidthFull();
        styleField(emailField);

        quantityField = new IntegerField("Quantity");
        quantityField.setValue(1);
        quantityField.setMin(1);
        quantityField.setMax(10);
        quantityField.setWidthFull();

        addressField = new TextField("Shipping Address");
        addressField.setWidthFull();
        styleField(addressField);

        statusLabel = new Span("");
        statusLabel.getStyle().set("color", "#b00020").set("font-family", "Georgia, serif").set("font-size", "13px");

        Button submitBtn = new Button("Confirm Order");
        submitBtn.getStyle()
                .set("background-color", "#c9a98a")
                .set("color", "white")
                .set("font-family", "Georgia, serif")
                .set("border-radius", "6px")
                .set("padding", "10px 28px")
                .set("cursor", "pointer")
                .set("margin-top", "8px");
        submitBtn.addClickListener(e -> handleOrder());

        Button backBtn = new Button("← Back to Products");
        backBtn.getStyle()
                .set("background-color", "transparent")
                .set("color", "#a08878")
                .set("font-family", "Georgia, serif")
                .set("border", "1px solid #ddc9b8")
                .set("border-radius", "6px")
                .set("cursor", "pointer");
        backBtn.addClickListener(e -> UI.getCurrent().navigate(HomePage.class));

        card.add(star, title, productInfo, nameField, emailField, quantityField, addressField, statusLabel, submitBtn, backBtn);
        layout.add(card);
        return layout;
    }

    private void handleOrder() {
        String name = nameField.getValue().trim();
        String email = emailField.getValue().trim();
        String address = addressField.getValue().trim();
        int quantity = quantityField.getValue() != null ? quantityField.getValue() : 1;

        if (name.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please enter your name and email.");
            return;
        }
        if (address.isEmpty()) {
            statusLabel.setText("Please enter a shipping address.");
            return;
        }

        Customer customer = customerService.findOrCreate(name, email);
        Order order = orderService.placeOrder(customer, selectedProduct, quantity, address);

        nameField.clear();
        emailField.clear();
        addressField.clear();
        quantityField.setValue(1);
        statusLabel.getStyle().set("color", "#1b5e20");
        statusLabel.setText("✓ Order placed successfully! Thank you, " + customer.getName() + ".");
    }


    private void styleField(com.vaadin.flow.component.Component field) {
        field.getElement().getStyle()
                .set("background-color", "#fdf6f0")
                .set("border-radius", "6px");
    }

    private com.vaadin.flow.component.Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setJustifyContentMode(JustifyContentMode.CENTER);
        footer.getStyle()
                .set("background-color", "#5c4033")
                .set("padding", "20px");

        Span text = new Span("© 2025 PlanetMel Skin Co.");
        text.getStyle()
                .set("color", "#e8d5c4")
                .set("font-family", "Georgia, serif")
                .set("font-size", "12px")
                .set("font-style", "italic");

        footer.add(text);
        return footer;
    }
}

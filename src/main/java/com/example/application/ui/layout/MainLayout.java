package com.example.application.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * Shared application layout with a lightweight navigation drawer.
 */
@PWA(name = "Reservation App", shortName = "Reservation App", enableInstallPrompt = false)
@Theme(themeFolder = "myapp")
public class MainLayout extends AppLayout {

    private final H1 viewTitle = new H1();

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle.addClassNames("m-0", "text-l");
        Header header = new Header(toggle, viewTitle);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center", "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H1 appName = new H1("Skyward Reservation");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        Nav navigation = new Nav();
        navigation.addClassNames("border-b", "border-contrast-10");

        navigation.add(createMenuItem("Search flights", "la la-plane"));

        Footer footer = new Footer(new Span("Crafted for technical interviews"));
        footer.addClassNames("flex", "items-center", "px-m", "py-s", "text-s", "text-secondary");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName, navigation, footer);
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Component createMenuItem(String text, String iconClass) {
        RouterLink link = new RouterLink();
        link.setRoute(com.example.application.ui.view.SearchView.class);
        link.addClassNames("flex", "items-center", "px-m", "py-s", "text-secondary", "gap-s");
        Span icon = new Span();
        icon.addClassNames(iconClass, "text-l");
        Span label = new Span(text);
        label.addClassNames("font-medium");
        link.add(icon, label);
        return link;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}

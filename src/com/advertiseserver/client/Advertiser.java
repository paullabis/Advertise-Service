package com.advertiseserver.client;

import org.restlet.client.data.MediaType;
import org.restlet.client.data.Preference;
import org.restlet.client.resource.Result;

import com.advertiseserver.domain.Ad;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Advertiser implements EntryPoint {
    /**
     * Create a remote service proxy to talk to the server-side Contact
     * resource.
     */
    private final AdvertiseResourceProxy advertiseResource = GWT
            .create(AdvertiseResourceProxy.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button getButton = new Button("get");
        final Button updateButton = new Button("update");

        // Ad fields
        final TextBox cTbTitle = new TextBox();
        final TextBox cTbDescription = new TextBox();


        VerticalPanel adPanel = new VerticalPanel();
        adPanel.add(cTbTitle);
        adPanel.add(cTbDescription);
        RootPanel.get("adContainer").add(adPanel);

        RootPanel.get("buttonContainer").add(getButton);
        RootPanel.get("buttonContainer").add(updateButton);

        // Focus the cursor on the first name field when the app loads
        cTbTitle.setFocus(true);
        updateButton.setVisible(false);

        // Define a dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        // Get the ad
        getButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // Set up the ad resource
                advertiseResource.getClientResource().setReference(
                        "/ad/123");
                advertiseResource.getClientResource().getClientInfo()
                        .getAcceptedMediaTypes().add(
                                new Preference<MediaType>(
                                        MediaType.APPLICATION_JAVA_OBJECT_GWT));

                // Retrieve the ad
                advertiseResource.retrieve(new Result<Ad>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Get ad");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(Ad ad) {
                        // Ad fields
                        cTbTitle.setText(ad.getTitle());
                        cTbDescription.setText(ad.getDescription());
                        
                        updateButton.setVisible(true);
                    }
                });
            }
        });

        updateButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Ad ad = new Ad();
                ad.setTitle(cTbTitle.getValue());
                ad.setDescription(cTbDescription.getValue());

                // Update the ad
                advertiseResource.store(ad, new Result<Void>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Update ad");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(Void v) {
                        dialogBox.setText("Update ad");
                        textToServerLabel
                                .setText("Ad successfully updated");
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        });
    }
}
